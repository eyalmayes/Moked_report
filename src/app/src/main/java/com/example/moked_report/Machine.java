package com.example.moked_report;

public class Machine {

    String name;
    int image;
    String number;
    boolean isWork;
    String yNotInWork;
    public static Machine[] machines= new Machine[20];


    public Machine() {
    }

    public Machine(String name, int image,String number) {
        this.name = name;
        this.image = image;
        this.number = number;
    }

    public static void fillArray(){
        machines[0] = new Machine("Okuma MA",R.drawable.okumaa,"1");
        machines[1] = new Machine("Okuma MX",R.drawable.okumax,"2");
        machines[2] = new Machine("4800",R.drawable.img,"3");
        machines[3] = new Machine("V414",R.drawable.img,"4");
        machines[4] = new Machine("VTC 200B",R.drawable.img,"5");
        machines[5] = new Machine("AJV 18N",R.drawable.img,"6");
        machines[6] = new Machine("HERMLE C22",R.drawable.img,"7");
        machines[7] = new Machine("HERMLE C30",R.drawable.img,"8");
        machines[8] = new Machine("GROB G350",R.drawable.img,"9");
        machines[9] = new Machine("INTEGREX IV200",R.drawable.img,"10");
        machines[10] = new Machine("J200",R.drawable.img,"11");
        machines[11] = new Machine("SL-25",R.drawable.img,"12");
        machines[12] = new Machine("J200-SM",R.drawable.img,"13");
        machines[13] = new Machine("NL-2500",R.drawable.img,"14");
        machines[14] = new Machine("NL-2000",R.drawable.img,"15");
        machines[15] = new Machine("NLX-2000",R.drawable.img,"16");
        machines[16] = new Machine("SL-3TF",R.drawable.img,"17");
        machines[17] = new Machine("TAKISAWA",R.drawable.img,"18");
        machines[18] = new Machine("SL-1",R.drawable.img,"19");
        machines[19] = new Machine("SMART",R.drawable.img,"20");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isWork() {
        return isWork;
    }

    public void setWork(boolean work) {
        isWork = work;
    }

    public String getyNotInWork() {
        return yNotInWork;
    }

    public void setyNotInWork(String yNotInWork) {
        this.yNotInWork = yNotInWork;
    }
}
