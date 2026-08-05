package com.fitzone.util;

import com.fitzone.model.Member;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class CSVExporter {
    private static final Logger logger = LoggerFactory.getLogger(CSVExporter.class);

    public static boolean exportMembersToCSV(List<Member> members, File file) {
        try (FileWriter out = new FileWriter(file);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.builder().setHeader("ID", "First Name", "Last Name", "Email", "Mobile", "City", "State").build())) {

            for (Member m : members) {
                printer.printRecord(
                    m.getId(),
                    m.getFname(),
                    m.getLname(),
                    m.getEmail(),
                    m.getMobile(),
                    m.getCity(),
                    m.getState()
                );
            }
            printer.flush();
            logger.info("Exported {} members to CSV: {}", members.size(), file.getAbsolutePath());
            return true;
        } catch (Exception e) {
            logger.error("Failed to export members to CSV", e);
            return false;
        }
    }
}
