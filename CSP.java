package csp;

import java.util.*;

/**
 *
 * @author Bayu Aji Firmansyah
 */
public abstract class CSPProblem {

    Map<Variable, List<Constraint>> VariableConstrains;

    public abstract List<Variable> variables();

    public abstract List<Constraint> constraints();

    public boolean satisfiedByAssignment(Assignment asign) {
        if (variables().size() > asign.size()) {
            return false;
        }
        for (Constraint c : constraints()) {
            if (c.satisfied(asign)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public List<Constraint> variableConstraints(Variable v) {
        if (VariableConstrains != null) {
            return VariableConstrains.get(v);
        }
        VariableConstrains = new HashMap<Variable, List<Constraint>>();
        for (Constraint c : constraints()) {
            List<Variable> vars = c.reliesOn();
            for (Variable constrVar : vars) {
                if (VariableConstrains.containsKey(constrVar)) {
                    VariableConstrains.get(constrVar).add(c);
                } else {
                    List<Constraint> constr = new LinkedList<Constraint>();
                    constr.add(c);
                    VariableConstrains.put(constrVar, constr);
                }
            }
        }
        return VariableConstrains.get(v);
    }

    public List<Object> domainValues(Assignment assign, Variable v) {
        List<Object> domain = assign.getDomain(v);
        if (domain != null) {
            return domain;
        }
        return v.domain();
    }

    public boolean consistentAssignment(Assignment assign, Variable v) {
        for (Constraint c : constraints()) {
            if (!c.consistent(assign)) {
                return false;
            }
        }
        return true;
    }

    public List<Variable> getVariables() {
        return Collections.unmodifiableList(variables());
    }

    public Assignment interference(Assignment assign, Variable v) throws IllegalStateException {
        return assign;
    }
}
