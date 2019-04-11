package name.martingeisse.mahdl.intellij;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import name.martingeisse.mahdl.common.Environment;
import name.martingeisse.mahdl.common.ReferenceResolutionException;
import name.martingeisse.mahdl.input.cm.CmNode;
import name.martingeisse.mahdl.input.cm.Module;
import name.martingeisse.mahdl.input.cm.QualifiedModuleName;
import name.martingeisse.mahdl.input.cm.impl.InternalPsiUtil;
import name.martingeisse.mahdl.input.cm.impl.QualifiedModuleNameImpl;
import name.martingeisse.mahdl.intellij.input.PsiUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class IntellijEnvironment implements Environment {

	public static void initialize() {
		if (Holder.INSTANCE == null) {
			Holder.INSTANCE = new IntellijEnvironment();
		}
	}

	@Override
	public InputStream openDataFile(CmNode anchor, String filename) throws IOException {
		PsiElement anchorPsi = InternalPsiUtil.getPsiFromCm(anchor);

		if (filename.indexOf('/') != -1 || filename.startsWith(".")) {
			throw new IOException("invalid filename: " + filename);
		}
		PsiFile psiFile = anchorPsi.getContainingFile();
		if (psiFile == null) {
			throw new IOException("element is not inside a PsiFile");
		}
		VirtualFile containingFile = psiFile.getOriginalFile().getVirtualFile();
		if (containingFile == null) {
			throw new IOException("element is not inside a VirtualFile");
		}
		VirtualFile folder = containingFile.getParent();
		VirtualFile file = folder.findChild(filename);
		if (file == null) {
			throw new IOException("file not found: " + filename);
		}
		return file.getInputStream();
	}

	@Override
	public void validateModuleNameAgainstFilePath(Module module, QualifiedModuleName name) throws IOException {
		QualifiedModuleNameImpl namePsi = (QualifiedModuleNameImpl) InternalPsiUtil.getPsiFromCm(name);
		Module moduleForName;
		try {
			moduleForName = PsiUtil.resolveModuleName(namePsi);
		} catch (ReferenceResolutionException e) {
			throw new IOException("could not resolve name", e);
		}
		if (moduleForName != module) {
			throw new IOException("name resolves to different module");
		}
	}

	@Override
	public Module resolveModuleReference(QualifiedModuleName name) throws ReferenceResolutionException {
		QualifiedModuleNameImpl namePsi = (QualifiedModuleNameImpl) InternalPsiUtil.getPsiFromCm(name);
		PsiElement untypedResolvedModule = namePsi.getReference().resolve();
		if (untypedResolvedModule instanceof Module) {
			return (Module) untypedResolvedModule;
		} else {
			throw new ReferenceResolutionException("unknown module: '" + namePsi.getReference().getCanonicalText() + "'");
		}
	}

}
