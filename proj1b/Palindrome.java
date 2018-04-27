public class Palindrome {
    private Deque d;

    public Palindrome() {
        d = new LinkedListDeque();
    }

    public Deque<Character> wordToDeque(String word) {
        for (int i = 0; i < word.length(); i++) {
            d.addLast(word.charAt(i));
        }
        return d;
    }

    public boolean isPalindrome(String word) {
        if (word == null || word.isEmpty() || word.length() == 1) {
            return true;
        }

        String compare = "";
        d = this.wordToDeque(word);
        for (int i = 0; i < word.length(); i++) {
            compare += d.removeLast();
        }
        return word.equals(compare);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int n = word.length();

        if (word == null || word.isEmpty() || n == 1) {
            return true;
        }
        for (int i = 0; i < n / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(n - i - 1))) {
                return false;
            }
        }
        return true;
    }
}
