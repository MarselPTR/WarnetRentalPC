import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerDataHandler {
    private static final String CUSTOMER_FILE = "customers.txt";

    // Memuat data pelanggan dari file
    public static Map<String, String> getCustomerPasswords() {
        ensureCustomerFileExists();
        Map<String, String> customerPasswords = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    customerPasswords.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerPasswords;
    }

    // Menambahkan pelanggan baru
    public static boolean addCustomer(String name, String password) {
        ensureCustomerFileExists();
        if (getCustomerPasswords().containsKey(name)) {
            return false; // Nama pelanggan sudah ada
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.write(name + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true; // Pelanggan berhasil ditambahkan
    }

    // Membuat file jika belum ada
    private static void ensureCustomerFileExists() {
        File file = new File(CUSTOMER_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
