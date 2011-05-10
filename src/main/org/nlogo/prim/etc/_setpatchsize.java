package org.nlogo.prim.etc;

import org.nlogo.api.LogoException;
import org.nlogo.nvm.Command;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Syntax;

public final strictfp class _setpatchsize
    extends Command {
  @Override
  public Syntax syntax() {
    return Syntax.commandSyntax(
        new int[]{Syntax.TYPE_NUMBER},
        "O---", true);
  }

  @Override
  public void perform(final Context context) throws LogoException {
    final double newPatchSize = argEvalDoubleValue(context, 0);
    if (newPatchSize != workspace.patchSize()) {
      workspace.waitFor
          (new org.nlogo.api.CommandRunnable() {
            public void run() {
              workspace.setDimensions(
                  workspace.world().getDimensions(), newPatchSize);
            }
          });
      workspace.waitFor
          (new org.nlogo.api.CommandRunnable() {
            public void run() {
              workspace.resizeView();
            }
          });
    }
    context.ip = next;
  }
}
