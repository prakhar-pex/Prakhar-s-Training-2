#include <iostream>
#include <unordered_set>
#include <string>
using namespace std;

int lengthOfLongestSubstring(string s) {
    unordered_set<char> st;
    int left = 0, right = 0, maxLen = 0;

    while (right < s.size()) {
        if (st.find(s[right]) == st.end()) { //if element does not exist in set 
            st.insert(s[right]);
            maxLen = max(maxLen, right - left + 1);
            right++;
        } else {
            st.erase(s[left]);
            left++;
        }
    }

    return maxLen;
}

int main() {
    string s = "abcabcbb";
    cout << lengthOfLongestSubstring(s) << endl;
    return 0;
}
