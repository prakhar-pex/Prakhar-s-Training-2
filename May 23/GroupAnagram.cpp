#include <bits/stdc++.h>
using namespace std;

vector<vector<string>> groupAnagrams(vector<string>& strs) {
    map<string, vector<string>> mp; // "aet": ["eat", "tea", "ate"] 

    for (int i = 0; i < strs.size(); i++) {
        string sortedStr = strs[i];
        sort(sortedStr.begin(), sortedStr.end());
        mp[sortedStr].push_back(strs[i]); // use original string
    }

    vector<vector<string>> res;
    for (auto& it : mp) { // it is the elements in the map. 
        //it.second , i.e. for key ->"aet" , we take the entire value ->["eat", "tea", "ate"] of this key.
        res.push_back(it.second);
    }

    return res;
}

int main() {
    vector<string> input = {"eat", "tea", "tan", "ate", "nat", "bat"};
    vector<vector<string>> res = groupAnagrams(input);

    for (int i = 0; i < res.size(); i++) {
        for (int j = 0; j < res[i].size(); j++) {
            cout << res[i][j] << " ";
        }
        cout << endl;
    }
    return 0;
}
