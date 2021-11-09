package engine.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Answer {
    private int[] answer;

    @JsonCreator
    Answer(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswer() {
        return answer;
    }
}
