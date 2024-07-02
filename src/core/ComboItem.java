package core;

public class ComboItem {
    private int key;// Kombinasyon öğesinin anahtar değeri
    private String value;// Kombinasyon öğesinin görüntülenen değeri

    //Yapıcı Metod
    public ComboItem(int key, String value) {
        this.key = key;
        this.value = value;
    }


    public int getKey() {
        return key;
    }// Anahtar değerini döndüren metod

    public void setKey(int key) {
        this.key = key;
    }// Anahtar değeri ayarlayan metod

    public String getValue() {
        return value;
    }    // Görüntülenen değeri döndüren metod


    public void setValue(String value) {
        this.value = value;
    }    // Görüntülenen değeri ayarlayan metod

    // Nesneyi metin olarak temsil eden metod
    public String toString() {
        return this.value;
    }
}
