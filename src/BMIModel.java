public class BMIModel {

    // Validate inputs and calculate BMI.
    public double calculateBMI(double weight, double height, boolean metric) {

        if (!Double.isFinite(weight)
                || !Double.isFinite(height)
                || weight <= 0
                || height <= 0) {

            throw new IllegalArgumentException(
                    "Weight and height must be positive, finite numbers."
            );
        }

        // Metric units: kilograms and metres.
        double bmi = weight / height / height;

        if (!metric) {
            // English units: pounds and inches.
            bmi *= 703;
        }

        if (!Double.isFinite(bmi) || bmi <= 0) {
            throw new IllegalArgumentException(
                    "These values cannot produce a valid BMI."
            );
        }

        return bmi;
    }

    // Determine the category before rounding.
    public String getCategory(double bmi) {

        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }
}
