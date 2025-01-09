package ee.stefanie.veebipood.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Andmed {
    public ArrayList<TimestampPrice> ee;
    public ArrayList<TimestampPrice> fi;
    public ArrayList<TimestampPrice> lv;
    public ArrayList<TimestampPrice> lt;
}
