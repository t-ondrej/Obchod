package sk.upjs.ics.obchod.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.obchod.entity.Tovar;

public enum KosikModel {
    INSTANCE;

    private final ObservableList<TovarModel> tovarKosika = FXCollections.observableArrayList();

    public ObservableList<TovarModel> getTovarKosika() {
        return this.tovarKosika;
    }

    public void pridajTovarDoKosika(Tovar tovar, int pocetTovaru) {

        for (int i = 0; i < tovarKosika.size(); i++) {
            TovarModel tovarModel = tovarKosika.get(i);

            if (tovarModel.getNazov().getValue().equals(tovar.getNazov())) {
                tovarModel.setPocetKusovVKosiku(pocetTovaru);
                return;
            }
        }

        TovarModel novyTovarModel = new TovarModel(tovar.getNazov(), tovar.getCena(), pocetTovaru);
        tovarKosika.add(novyTovarModel);
    }

    public void odoberTovarKosika(Tovar tovar) {
        for (int i = 0; i < tovarKosika.size(); i++) {
            TovarModel tovarModel = tovarKosika.get(i);
            if (tovarModel.getNazov().getValue().equals(tovar.getNazov())) {
                tovarModel.setPocetKusovVKosiku(tovarModel.getPocetKusovVKosiku().getValue() - 1);
                if (tovarModel.getPocetKusovVKosiku().getValue() == 0) {
                    tovarKosika.remove(tovarModel);
                }
                return;
            }
        }
    }
}
