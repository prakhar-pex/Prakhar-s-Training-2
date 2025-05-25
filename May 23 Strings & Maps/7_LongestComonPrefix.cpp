#include <bits/stdc++.h>
using namespace std;

string longestCommonPrefix(vector<string>& strs) {

        //sorting makes it easy for us to compare the strings 
        sort(strs.begin(), strs.end());  // Sort lexicographically (only array is sorted, not the characters)
        
        // for (auto it:strs){
        //     cout<<it;
        // }
        int n = strs.size();
        string result = "";

        // Compare only first and last strings after sorting
        int size = strs[0].size();
        for (int i = 0; i < size; i++) {
            if (strs[0][i] == strs[n - 1][i]) {
                result.push_back(strs[0][i]);
            } else {
                break;
            }
        }

        return result;
}

int main() {
    vector<string> strs = {"flower", "flow", "flight"};
    
    string prefix = longestCommonPrefix(strs);
    cout << prefix << endl;
    return 0;
}
