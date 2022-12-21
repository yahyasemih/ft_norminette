package ma.leet.ft_norminette;

import com.intellij.codeInspection.ProblemsHolder;
import com.jetbrains.cidr.lang.inspections.OCInspection;
import org.jetbrains.annotations.NotNull;

public class NormCodeInspection extends OCInspection {

    @NotNull
    @Override
    public NormInspectionVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new NormInspectionVisitor(holder);
    }
}
