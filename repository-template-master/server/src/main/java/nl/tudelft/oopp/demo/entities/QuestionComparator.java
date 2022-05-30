package nl.tudelft.oopp.demo.entities;

import java.util.Comparator;

public class QuestionComparator implements Comparator<Question> {
    /**
     * Method that compares 2 Questions in the Room
     * in terms of their priority.
     *
     * @param q1 the first Question
     * @param q2 the second Question
     * @return the priority between q1 and q2 as an integer
     */
    public int compare(Question q1, Question q2) {
        return Double.compare(q2.getPriority(), q1.getPriority());
    }
}
