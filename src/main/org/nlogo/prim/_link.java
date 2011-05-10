package org.nlogo.prim;

import org.nlogo.agent.Link;
import org.nlogo.api.LogoException;
import org.nlogo.api.Nobody;
import org.nlogo.nvm.Context;
import org.nlogo.nvm.Reporter;
import org.nlogo.nvm.Syntax;

public final strictfp class _link
    extends Reporter {
  @Override
  public Object report(final Context context) throws LogoException {
    Link link = world.getLink(argEvalDouble(context, 0),
        argEvalDouble(context, 1), world.links());
    if (link == null) {
      return Nobody.NOBODY;
    }
    return link;
  }

  @Override
  public Syntax syntax() {
    return Syntax.reporterSyntax
        (new int[]{Syntax.TYPE_NUMBER, Syntax.TYPE_NUMBER},
            Syntax.TYPE_LINK | Syntax.TYPE_NOBODY);
  }
}
