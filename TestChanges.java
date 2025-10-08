// Simple test to verify our changes
public class TestChanges {
    public static void main(String[] args) {
        // Test Email with empty string
        try {
            seedu.address.model.person.Email email = new seedu.address.model.person.Email("");
            System.out.println("✓ Empty email works: " + email);
        } catch (Exception e) {
            System.out.println("✗ Empty email failed: " + e.getMessage());
        }
        
        // Test Address with empty string
        try {
            seedu.address.model.person.Address address = new seedu.address.model.person.Address("");
            System.out.println("✓ Empty address works: " + address);
        } catch (Exception e) {
            System.out.println("✗ Empty address failed: " + e.getMessage());
        }
        
        // Test Address with space (should fail)
        try {
            seedu.address.model.person.Address address = new seedu.address.model.person.Address(" ");
            System.out.println("✗ Space address should have failed but didn't: " + address);
        } catch (Exception e) {
            System.out.println("✓ Space address correctly failed: " + e.getMessage());
        }
    }
}