package com.scaa.scaa.ui.expert;


import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Possibility;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExpertUIFrame extends JFrame  {
    private Choice toReturn;
    GridLayout experimentLayout = new GridLayout(0, 1);

    public ExpertUIFrame() {
        initComponents();
    }

    private void initComponents() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setTitle("Expert UI");
    }

    public void noChoicesToDo() {
        JLabel jLabel = new JLabel("Pas de choix à faire");
        this.add(jLabel);
    }

    public Choice askForExpertChoice(List<Possibility> possibilities) throws InterruptedException {
        JPanel pan = new JPanel();
        pan.setLayout(experimentLayout);
        this.setContentPane(pan);
        ComponentServiceRelation asker = possibilities.get(0).getAsker();
        pan.add(new JLabel("Le composant à l'origine du conflit est "+asker.getComponent().getName()));
        pan.add(new JLabel("Son service à connecter est "+asker.getService().getName()));
        pan.add(new JLabel("La description de ce service est : "+asker.getService().getDescription()));
        pan.add(new JLabel("Ce service est : "+(asker.isRequired() ? "requis" : "fourni")));
        pan.add(new JLabel("Voici les candidats : "));
        pan.add(new JLabel(""));
        for (Possibility possibility : possibilities) {
            JPanel candidatePan = new JPanel();
            candidatePan.setLayout(new GridLayout(0, 2));

            JLabel desc = new JLabel("Nom du composant : "+possibility.getCandidate().getComponent().getName()+" / Nom du service : "
                    + possibility.getCandidate().getService().getName() + " / Description du service : " + possibility.getCandidate().getService().getDescription());
            String txt = possibility.isPreferable() ? "<html><font color='red'>Préférentiel</font> - Vous pouvez faire ce choix de manière définitive ou non</html>"
                    : "Vous pouvez faire ce choix de manière définitive ou non";
            JLabel pos = new JLabel(txt);

            JButton jButton = new JButton("Non définitif");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toReturn = new Choice(false, asker, possibility.getCandidate());
                }
            });

            JButton jButton2 = new JButton("Définitif");
            jButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    toReturn = new Choice(true, asker, possibility.getCandidate());
                }
            });
            candidatePan.add(desc);
            candidatePan.add(pos);
            candidatePan.add(jButton);
            candidatePan.add(jButton2);
            pan.add(candidatePan);
            this.setVisible(true);
        }
        while (toReturn == null) {}
        Choice toReturn2 = new Choice(toReturn.isDefinitive(), toReturn.getAsker(), toReturn.getChosen());
        toReturn = null;
        pan.removeAll();
        pan.revalidate();
        pan.repaint();
        noChoicesToDo();
        return toReturn2;
    }
}
