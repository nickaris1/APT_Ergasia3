package gr.upatras.Ergasia3;

public class Temperature {
    private double temperature = 0.0d;

    public Temperature() {} //for deserialisation

    public Temperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
