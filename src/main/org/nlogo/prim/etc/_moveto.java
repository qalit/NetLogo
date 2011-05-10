package org.nlogo.prim.etc;

import org.nlogo.agent.Agent;
import org.nlogo.api.AgentException;
import org.nlogo.agent.Turtle;
import org.nlogo.api.I18N;
import org.nlogo.api.LogoException;
import org.nlogo.nvm.Command;
import org.nlogo.nvm.EngineException;
import org.nlogo.nvm.Syntax;

public final strictfp class _moveto
    extends Command {
  @Override
  public Syntax syntax() {
    int[] right = {Syntax.TYPE_TURTLE | Syntax.TYPE_PATCH};
    return Syntax.commandSyntax(right, "OT--", true);
  }

  @Override
  public void perform(final org.nlogo.nvm.Context context)
      throws LogoException {
    Agent otherAgent = argEvalAgent(context, 0);
    if (otherAgent.id == -1) {
      throw new EngineException(context, this, I18N.errors().get("org.nlogo.$common.thatTurtleIsDead"));
    }
    if (otherAgent instanceof org.nlogo.agent.Link) {
      throw new EngineException(context, this, "you can't move-to a link");
    }
    try {
      if (context.agent instanceof Turtle) {
        ((Turtle) context.agent).moveTo(otherAgent);
      } else {
        world.observer().moveto(otherAgent);
      }
    } catch (AgentException ex) {
      throw new EngineException(context, this, ex.getMessage());
    }


    context.ip = next;
  }
}
