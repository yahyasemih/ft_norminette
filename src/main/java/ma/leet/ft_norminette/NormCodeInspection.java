package ma.leet.ft_norminette;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.cidr.lang.inspections.OCInspection;
import org.jetbrains.annotations.NotNull;

public class NormCodeInspection extends OCInspection {

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new NormInspectionVisitor(holder);
    }
}
