package Message;

import java.util.ArrayList;
import java.util.Map;

public class MsgAnswer extends Msg
{
    private String failure;
    private String warning;
    private Integer interval;
    private Integer min_interval;
    private Integer complete;
    private Integer incomplete;
    private ArrayList<Map<String,Object>> players;

    public MsgAnswer(String _failure)
    {
        super((byte) 11);

        warning = null;
        min_interval = null;
        interval = null;
        complete = null;
        incomplete = null;
        players = null;

        failure = _failure;
    }

    public MsgAnswer(Integer _interval, Integer _complete, Integer _incomplete, ArrayList<Map<String,Object>> _players)
    {
        super((byte) 11);

        failure = null;
        warning = null;
        min_interval = null;

        interval = _interval;
        complete = _complete;
        incomplete = _incomplete;
        players = _players;
    }

    public String getFailure() { return failure; }
    public String getWarning() { return warning; }
    public Integer getInterval() { return interval; }
    public Integer getMin_interval() { return min_interval; }
    public Integer getComplete() { return complete; }
    public Integer getIncomplete() { return incomplete; }
    public ArrayList<Map<String,Object>> getPlayers() { return players; }

    public void setWarning(String _warning) { warning = _warning; }
    public void setMin_interval(Integer _min_interval) { min_interval = _min_interval; }

    /**
     * ToString method
     */
    @Override
    public String toString()
    {
        String result = "MsgAnswer :";

        if(failure != null) return result + " failure: " + failure;

        if(warning != null) result += " warning: " + warning;
        result += " interval: " + Integer.toString(interval);
        if(min_interval != null) result += " min_interval: " + Integer.toString(min_interval);
        result += " complete: " + Integer.toString(complete);
        result += " incomplete: " + Integer.toString(incomplete);
        result += " players: " + players.toString();

        return result;
    }
}
