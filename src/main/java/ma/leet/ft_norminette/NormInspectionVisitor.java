package ma.leet.ft_norminette;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.TextRange;
import com.jetbrains.cidr.lang.psi.OCFile;
import com.jetbrains.cidr.lang.psi.visitors.OCVisitor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class NormInspectionVisitor extends OCVisitor {
    private static final Runtime runtime = Runtime.getRuntime();
    private final ProblemsHolder holder;

    public NormInspectionVisitor(@NotNull ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitOCFile(@NotNull OCFile file) {
        super.visitOCFile(file);
        if (!file.getName().endsWith(".c") && !file.getName().endsWith(".h")) {
            return;
        }
        if (file.getTextLength() > 15000) {
            holder.registerProblem(file.getOriginalElement(),
                    "File too long, checking norm will not be performed",
                    ProblemHighlightType.WARNING,
                    (LocalQuickFix) null);
            return;
        }

        try {
            Path tmpdir = Files.createTempDirectory("clion");
            Path path = Path.of(tmpdir + "/" + file.getName());
            Files.writeString(path, file.getText());

            Process process = runtime.exec(new String[]{"norminette", path.toString()});
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            stdInput.readLine();

            highlightNormErrors(file, stdInput);
            stdInput.close();
            Files.deleteIfExists(path);
        } catch (IOException e) {
            holder.registerProblem(file,
                    "Error while checking norm: " + e.getMessage(),
                    ProblemHighlightType.ERROR,
                    TextRange.create(0, file.getTextLength()),
                    (LocalQuickFix) null);
            e.printStackTrace();
        }
    }

    private void highlightNormErrors(@NotNull OCFile file, BufferedReader stdInput) throws IOException {
        Map<Integer, Integer> lineToOffsetMapping = getLineToOffsetMapping(file);
        String line;

        while ((line = stdInput.readLine()) != null) {
            if (!line.startsWith("Error:")) {
                continue;
            }
            int lineNumber = parseLineNumber(line);
            int startOffset = lineToOffsetMapping.get(lineNumber);
            int endOffset = lineToOffsetMapping.getOrDefault(lineNumber + 1, file.getTextLength() + 1) - 1;
            int errorIndex = line.indexOf("):");

            if (errorIndex != -1) {
                holder.registerProblem(file,
                        line.substring(errorIndex + 3),
                        ProblemHighlightType.WEAK_WARNING,
                        TextRange.create(startOffset, endOffset),
                        (LocalQuickFix) null);
            }
        }
    }

    private static int parseLineNumber(String line) {
        int columnIndex = line.indexOf("(line:");
        int commaIndex = line.indexOf(',');
        if (columnIndex == -1 || commaIndex == -1) {
            return 1;
        }
        return Integer.parseInt(line.substring(columnIndex + 6, commaIndex).trim());
    }

    private static Map<Integer, Integer> getLineToOffsetMapping(OCFile file) {
        Map<Integer, Integer> map = new HashMap<>();
        String text = file.getText();
        map.put(1, 0);

        int offset = 0;
        int line = 1;
        while ((offset = text.indexOf('\n', offset)) != -1) {
            map.put(++line, ++offset);
        }
        return map;
    }
}
