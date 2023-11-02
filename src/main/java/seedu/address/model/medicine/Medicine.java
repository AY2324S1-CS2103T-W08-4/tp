package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;


/**
 * Represents a Medicine in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Medicine {

    public static final String MESSAGE_MEDICINE_NAME_CONSTRAINTS =
            "Medicine Names should not be empty"; // temporarily this will change soon

    public static final String MESSAGE_SHORT_FORM_CONSTRAINTS =
            "Medicine short forms should not be empty"; // temporarily this will change soon



    public final String medicineName;

    public final String shortForm;
    /**
     * Every field must be present and not null.
     */
    public Medicine(String medicineName) {
        requireNonNull(medicineName);
        checkArgument(isValidMedicineName(medicineName), MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        this.medicineName = medicineName;
        this.shortForm = "";
    }

    /**
     * Every field must be present and not null.
     */
    public Medicine(String medicineName, String shortForm) {
        requireAllNonNull(medicineName, shortForm);
        checkArgument(isValidMedicineName(medicineName), MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        this.medicineName = medicineName;
        this.shortForm = shortForm;
    }

    public String getMedicineName() {
        return this.medicineName;
    }

    public String getShortForm() {
        return this.shortForm;
    }


    /**
     * Returns true if both medicines have the same medicine name.
     * This defines a weaker notion of equality between two medicines.
     */
    public boolean isSameMedicine(Medicine m) {
        if (m == this) {
            return true;
        }
        if (m == null) {
            return false;
        }
        return (medicineName.toLowerCase().equals(m.medicineName.toLowerCase())
                || medicineName.toLowerCase().equals(m.shortForm.toLowerCase())
                || m.shortForm.toLowerCase().equals(medicineName.toLowerCase()));
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Medicine)) {
            return false;
        }

        Medicine otherMedicine = (Medicine) o;
        return medicineName.equals(otherMedicine.medicineName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicineName);
    }

    @Override
    public String toString() {
        return "[ " + medicineName + " ]";
    }

    public static boolean isValidMedicineName(String s) {
        return !s.isEmpty();
    }

    public static boolean isValidShortForm(String s) {
        return true; //same as above
    }
}
