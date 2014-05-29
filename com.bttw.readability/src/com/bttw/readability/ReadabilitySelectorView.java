package com.readability;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class ReadabilitySelectorView {

    public static ArrayList<String> formulaeSelected = new ArrayList<String>();
    ArrayList<IReadabilityIndex> resultCollection;
    public Group leftButtonCol;

    public static boolean sylAlgorithmLIANG = false;
    public static boolean sylAlgorithmDICT = false;
    public static boolean sylAlgorithmRuleBased = false;
    public static boolean sylAlgorithmCombination = false;
    public static boolean senAlgorithmRULE = false;
    public static boolean senAlgorithmDATA = false;
    private static String extract;

    private enum retval {

        BACK, EXIT
    }
    private static boolean close = false;

    /**
     *
     * @param display
     * @param selectedFormulae
     * @param selectedAlgorithm
     * @param text
     * @param resultCollection
     * @return
     */
    public static retval openResultWindow(Display display, ArrayList<String> selectedFormulae, String text, ArrayList<IReadabilityIndex> resultCollection) {
        final Shell shell2 = new Shell(display);
        shell2.setLayout(new GridLayout());
        Table table = new Table(shell2, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
        data.heightHint = 200;
        data.widthHint = 400;
        table.setLayoutData(data);

        String[] titles = {"Formula", "Rating", "Explanation"};
        for (int i = 0; i < titles.length; i++) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
        }

      
            for (Iterator<String> it = selectedFormulae.iterator(); it.hasNext();) {
                String s = it.next();
                  for (IReadabilityIndex index : resultCollection) {
                if (s.equals(index.getTitle())) {
                    it.remove();
                    TableItem item = new TableItem(table, SWT.NONE);
                    item.setText(0, index.getTitle());
                    if (sylAlgorithmRuleBased && senAlgorithmRULE) {
                        item.setText(1, String.valueOf(index.getRatingRaR()));
                    } else if (sylAlgorithmRuleBased && senAlgorithmDATA) {
                        item.setText(1, String.valueOf(index.getRatingRaD()));
                    } else if (sylAlgorithmCombination && senAlgorithmDATA) {
                        item.setText(1, String.valueOf(index.getRatingCaD()));
                    } else if (sylAlgorithmCombination && senAlgorithmRULE) {
                        item.setText(1, String.valueOf(index.getRatingCaR()));
                    } else if (sylAlgorithmLIANG && senAlgorithmRULE) {
                        item.setText(1, String.valueOf(index.getRatingLaR()));
                    } else if (sylAlgorithmLIANG && senAlgorithmDATA) {
                        item.setText(1, String.valueOf(index.getRatingLaD()));
                    }

                    item.setText(2, index.getDescription());
                }
            }
        }
        for (int i = 0; i < titles.length; i++) {
            table.getColumn(i).pack();
        }

        //Holds Button Widgets
        final Composite optionButtons = new Composite(shell2, SWT.INHERIT_FORCE);
        GridLayout optsLayout = new GridLayout();

        //Buttons Layout
        optsLayout.numColumns = 2;
        optsLayout.horizontalSpacing = 30;
        optsLayout.makeColumnsEqualWidth = true;
        optsLayout.marginRight = 20;

        GridData data1 = new GridData();
        data1.horizontalAlignment = SWT.FILL;
        data1.grabExcessHorizontalSpace = true;
        data1.widthHint = 150;
        data1.horizontalAlignment = SWT.CENTER;


        optionButtons.setLayout(optsLayout);
        optionButtons.setLayoutData(data1);

        //Option Buttons

        //Save results


        final ArrayList<IReadabilityIndex> readability = resultCollection;
        final ArrayList<String> formulae = selectedFormulae;

        Button save = new Button(optionButtons, SWT.PUSH);
        save.setText("Save to File");

        save.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog fileDialog = new FileDialog(shell2, SWT.SAVE);
                fileDialog.setText("Save");

                String filterPath = "/";
                String platform = SWT.getPlatform();
                if (platform.equals("win32") || platform.equals("wpf")) {
                    filterPath = "c:\\";
                }

                fileDialog.setFilterPath(filterPath);
                String[] filterExt = {"*.txt", "*.*"};
                fileDialog.setFilterExtensions(filterExt);
                String selected = fileDialog.open();
                String fileNameExtracted = extractFileName(selected);
                if (selected == null) {
                    return;
                }


                BufferedWriter writer = null;
                try {


                    writer = new BufferedWriter(new FileWriter(selected));
                    String lineSeparator = System.getProperty("line.separator");

                    String output;
                    output = "TITLE:" + fileNameExtracted + lineSeparator + "EXTRACT:" + extract + lineSeparator;

                    for (IReadabilityIndex index : readability) {
                        output = output + index.toString(index.getRatingRaR()) + lineSeparator;
                    }
                    writer.write(output);
                    writer.close();


                } catch (IOException ioe) {
                    MessageBox messageBox = new MessageBox(shell2, SWT.ICON_ERROR
                            | SWT.OK);
                    messageBox.setMessage("File I/O Error.");
                    messageBox.setText("Error");
                    messageBox.open();
                    return;
                }
            }
        });





        //Go back to Algorithm selection
        Button back = new Button(optionButtons, SWT.PUSH);
        back.setText("Back");

        back.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                System.out.println("Cancel");
                sylAlgorithmLIANG = false;
                sylAlgorithmDICT = false;
                sylAlgorithmRuleBased = false;
                sylAlgorithmCombination = false;
                senAlgorithmRULE = false;
                senAlgorithmDATA = false;
                formulaeSelected.clear();
                //  formulaeSelected.subList(0, formulaeSelected.size()).clear();
                close = true; //Close This window and reopen previous one
            }
        });




        shell2.pack();
        shell2.open();

        while (!shell2.isDisposed()) {
            if (!display.readAndDispatch()) {

                if (close) {
                    shell2.dispose();
                    close = false;
                    return retval.BACK;
                }

                display.sleep();


            }
        }
        return retval.BACK;

    }

    /**
     *
     * @param readability
     * @param model
     */
    public void selectionPopUp(final com.readability.ReadabilityIndices readability, final Model model) {
        extract = model.getExtract();
        final Display display = new Display();
        //final Shell shell = new Shell(display, SWT.SHELL_TRIM & (~SWT.RESIZE)); //Make non-resizeable
        final Shell shell = new Shell(display, SWT.SHELL_TRIM); //Make non-resizeable
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.horizontalSpacing = 30;
        gridLayout.makeColumnsEqualWidth = true;
        gridLayout.marginRight = 20;

        shell.setLocation(520, 300);
        shell.setText("Readability Indices");

        shell.setLayout(gridLayout);

        /* Left column containing buttons */

        leftButtonCol = new Group(shell, SWT.NONE);
        leftButtonCol.setText("Select Readability Index(es)");

        GridLayout leftColLayout = new GridLayout(1, true);
        leftColLayout.marginHeight = 10;
        leftColLayout.verticalSpacing = 10;
        leftButtonCol.setLayout(leftColLayout);

        GridData data = new GridData();
        data.horizontalAlignment = SWT.FILL;
        data.grabExcessHorizontalSpace = true;
        data.widthHint = 220;

        final Button fleschGrdLevelChkBox = new Button(leftButtonCol, SWT.CHECK);
        fleschGrdLevelChkBox.setText("Flesch-Kincaid Grade Level");
        fleschGrdLevelChkBox.setLayoutData(data);

        fleschGrdLevelChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                //runButton.setEnabled(true);
                if (!fleschGrdLevelChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.FLESCHGRADELEVEL);
                } else {
                    formulaeSelected.add(FormulaDetails.FLESCHGRADELEVEL);
                }

            }
        });


        final Button fleschReadingEaseChkBox = new Button(leftButtonCol, SWT.CHECK);
        fleschReadingEaseChkBox.setText("Flesch Reading Ease");
        fleschReadingEaseChkBox.setLayoutData(data);

        fleschReadingEaseChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!fleschReadingEaseChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.FLESCHREADINGEASE);
                } else {
                    formulaeSelected.add(FormulaDetails.FLESCHREADINGEASE);
                }
            }
        });


        final Button smogIndexChkBox = new Button(leftButtonCol, SWT.CHECK);
        smogIndexChkBox.setText("SMOG");
        smogIndexChkBox.setLayoutData(data);
        smogIndexChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!smogIndexChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.SMOG);
                } else {
                    formulaeSelected.add(FormulaDetails.SMOG);
                }
            }
        });


        final Button coleManChkBox = new Button(leftButtonCol, SWT.CHECK);
        coleManChkBox.setText("Coleman-Liau Index");
        coleManChkBox.setLayoutData(data);
        coleManChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!coleManChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.COLEMANLIAU);
                } else {
                    formulaeSelected.add(FormulaDetails.COLEMANLIAU);
                }
            }
        });


        final Button aRiChkBox = new Button(leftButtonCol, SWT.CHECK);
        aRiChkBox.setText("Automated-Readability Index");
        aRiChkBox.setLayoutData(data);
        aRiChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!aRiChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.ARI);
                } else {
                    formulaeSelected.add(FormulaDetails.ARI);
                }
            }
        });

        final Button lixChkBox = new Button(leftButtonCol, SWT.CHECK);
        lixChkBox.setText("Lix");
        lixChkBox.setLayoutData(data);
        lixChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!lixChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.LIX);
                } else {
                    formulaeSelected.add(FormulaDetails.LIX);
                }
            }
        });


        final Button rixChkBox = new Button(leftButtonCol, SWT.CHECK);
        rixChkBox.setText("Rix");
        rixChkBox.setLayoutData(data);
        rixChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!rixChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.RIX);
                } else {
                    formulaeSelected.add(FormulaDetails.RIX);
                }
            }
        });

        /*      final Button daleChallChkBox = new Button(leftButtonCol, SWT.CHECK);
         daleChallChkBox.setText("Dale-Chall");
         daleChallChkBox.setLayoutData(data);
         daleChallChkBox.addSelectionListener(new SelectionAdapter() {
         @Override
         public void widgetSelected(SelectionEvent e) {
         if (!daleChallChkBox.getSelection()) {
         formulaeSelected.remove(FormulaDetails.DALECHALL);
         } else {
         formulaeSelected.add(FormulaDetails.DALECHALL);
         }
         }
         }); */

        final Button gunningFogChkBox = new Button(leftButtonCol, SWT.CHECK);
        gunningFogChkBox.setText("Gunning Fog");
        gunningFogChkBox.setLayoutData(data);
        gunningFogChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!gunningFogChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.GUNNINGFOG);
                } else {
                    formulaeSelected.add(FormulaDetails.GUNNINGFOG);
                }
            }
        });

        final Button foreCastChkBox = new Button(leftButtonCol, SWT.CHECK);
        foreCastChkBox.setText("FORECAST");
        foreCastChkBox.setLayoutData(data);
        foreCastChkBox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (!foreCastChkBox.getSelection()) {
                    formulaeSelected.remove(FormulaDetails.FORECAST);
                } else {
                    formulaeSelected.add(FormulaDetails.FORECAST);
                }
            }
        });

        //Right column containing radio buttons
        Composite rightCol = new Composite(shell, SWT.NONE);

        GridLayout rightGridLayout = new GridLayout();
        rightCol.setLayout(rightGridLayout);
        GridData gd3 = new GridData(GridData.FILL, SWT.TOP, true, true);
        gd3.widthHint = 220;
        rightGridLayout.verticalSpacing = 10;
        rightCol.setLayoutData(gd3);

        Group radioButtons = new Group(rightCol, SWT.NONE);
        radioButtons.setText("Syllabification Technique");

        GridLayout gridLayout2 = new GridLayout();
        gridLayout2.numColumns = 1;
        gridLayout2.marginHeight = 10;
        radioButtons.setLayout(gridLayout2);

        GridData gridData = new GridData(GridData.FILL, SWT.TOP, true, true);
        radioButtons.setLayoutData(gridData);

        final Button ruleBasedRadio = new Button(radioButtons, SWT.RADIO);
        ruleBasedRadio.setText("Rule-based");
        ruleBasedRadio.setToolTipText("Liangs Algorithm works by...");

        ruleBasedRadio.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sylAlgorithmRuleBased = true;
            }
        });

        final Button laingRadio = new Button(radioButtons, SWT.RADIO);
        laingRadio.setText("Liangs Algorithm");
        laingRadio.setToolTipText("x Algorithm works by...");
        laingRadio.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sylAlgorithmLIANG = true;
            }
        });

        final Button combRadio = new Button(radioButtons, SWT.RADIO);
        combRadio.setText("Liang & Rule-Based Combination");
        combRadio.setToolTipText("Dictionary Algorithm works by...");
        combRadio.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sylAlgorithmCombination = true;
            }
        });


        final Button dictRadio = new Button(radioButtons, SWT.RADIO);
        dictRadio.setText("Dictionary-based");
        dictRadio.setSelection(true);
        dictRadio.setEnabled(false);
        dictRadio.setToolTipText("Vowel count Algorithm works by...");
        dictRadio.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sylAlgorithmDICT = true;
            }
        });

        Group radioButtons12 = new Group(rightCol, SWT.NONE);
        radioButtons12.setText("Sentence Detection Technique");
        GridLayout gridLayout12 = new GridLayout();
        gridLayout12.numColumns = 1;
        gridLayout12.marginHeight = 10;
        radioButtons12.setLayout(gridLayout12);

        GridData gridData12 = new GridData(GridData.FILL, SWT.TOP, true, true);
        radioButtons12.setLayoutData(gridData12);

        final Button sentenceRuleRadio = new Button(radioButtons12, SWT.RADIO);
        sentenceRuleRadio.setText("Rule Based");
        sentenceRuleRadio.setToolTipText("Liangs Algorithm works by...");

        sentenceRuleRadio.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                senAlgorithmRULE = true;
            }
        });






        final Button sentenceDataDrivenRadio = new Button(radioButtons12, SWT.RADIO);
        sentenceDataDrivenRadio.setText("Data-Driven");
        sentenceDataDrivenRadio.setToolTipText("Liangs Algorithm works by...");

        sentenceDataDrivenRadio.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                senAlgorithmDATA = true;
                //algorithmSelected = algorithm.LIANG;
            }
        });





        Composite runAndCancelComposite = new Composite(rightCol, SWT.INHERIT_DEFAULT);
        runAndCancelComposite.setLayout(new GridLayout(2, true));
        //c2.setBackgroundMode(SWT.INHERIT_FORCE);

        GridData d = new GridData(GridData.FILL_HORIZONTAL, SWT.TOP, true, false);
        d.horizontalAlignment = SWT.END;
        d.grabExcessHorizontalSpace = true;


        runAndCancelComposite.setLayoutData(d);
        GridData runAndCancelGridData = new GridData();
        runAndCancelGridData.horizontalAlignment = SWT.BEGINNING;
        runAndCancelGridData.verticalAlignment = SWT.BOTTOM;
        runAndCancelGridData.grabExcessHorizontalSpace = true;
        runAndCancelGridData.widthHint = 220;

        //"Run" Button
        Button runButton = new Button(runAndCancelComposite, SWT.PUSH);
        runButton.setText("Run");
        runButton.setLayoutData(runAndCancelGridData);


        final ResultProcessor result = new ResultProcessor(model);

        /*  Display.getDefault().asyncExec(new Runnable() {
         public void run() {

         result.setResultCollection(model);
         // radioButton12.setText("Complete!");
         }
         }); **/

        resultCollection = result.getResultCollection();
        runButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                fleschGrdLevelChkBox.setSelection(false);
                fleschReadingEaseChkBox.setSelection(false);
                smogIndexChkBox.setSelection(false);
                coleManChkBox.setSelection(false);
                aRiChkBox.setSelection(false);
                lixChkBox.setSelection(false);
                rixChkBox.setSelection(false);
                //   daleChallChkBox.setSelection(false);
                // b9.setSelection(false);
                gunningFogChkBox.setSelection(false);
                foreCastChkBox.setSelection(false);
                sentenceDataDrivenRadio.setSelection(false);
                sentenceRuleRadio.setSelection(false);
                dictRadio.setSelection(false);
                combRadio.setSelection(false);
                laingRadio.setSelection(false);
                ruleBasedRadio.setSelection(false);
                MessageBox messageBox;
                int buttonID;


                if (formulaeSelected.size() > 0 && (senAlgorithmRULE || senAlgorithmDATA) && (sylAlgorithmRuleBased || sylAlgorithmLIANG
                        || sylAlgorithmCombination || sylAlgorithmDICT)) {

                    if (openResultWindow(display, formulaeSelected, "", resultCollection) == retval.EXIT) {
                        System.exit(0); //Exit if finished
                    } else {
                        leftButtonCol.redraw();
                        shell.setVisible(true); //Reinstate this window (Back button was used)
                    }
                } else {
                    messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                    // messageBox.setText("Warning");       
                    messageBox.setMessage("It is mandatory to select a Readability Index, Sentence Detection and Syllabification Algorithm!");
                    buttonID = messageBox.open();
                    switch (buttonID) {
                        case SWT.YES:
                    }


                }
            }
        });

        //Cancel Button
        Button cancelButton = new Button(runAndCancelComposite, SWT.PUSH);
        cancelButton.setText("Cancel");
        cancelButton.setLayoutData(runAndCancelGridData);
        cancelButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.setVisible(false);
            }
        });

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    public static String extractFileName(String filePathName) {
        if (filePathName == null) {
            return null;
        }

        int dotPos = filePathName.lastIndexOf('.');
        int slashPos = filePathName.lastIndexOf('\\');
        if (slashPos == -1) {
            slashPos = filePathName.lastIndexOf('/');
        }

        if (dotPos > slashPos) {
            return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0,
                    dotPos);
        }

        return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
    }
}