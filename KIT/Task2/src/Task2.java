import java.util.ArrayDeque;
import java.util.Deque;

public class Task2 {

    public static void main(String[] args) {
        System.out.println(check("([][[]()])"));
    }

    public static boolean check(String data) {
        Deque<Character> stack = new ArrayDeque<>();
        for(int i = 0; i < data.length() ; i++)
            switch(data.charAt(i)) {
                case('('):
                    stack.push('(');
                    break;
                case('['):
                    stack.push('[');
                    break;
                case(')'):
                    if(!stack.poll().equals('('))
                        return false;
                    break;
                case(']'):
                    if(!stack.poll().equals('['))
                        return false;
                    break;
            }
        if(stack.size() == 0)
            return true;
        else
            return false;
    }
}
