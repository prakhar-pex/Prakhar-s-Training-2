#include <bits/stdc++.h>
using namespace std;


    bool closeStrings(string word1, string word2) {
        if (word1.size()!=word2.size()) return false;

        vector<int> v1;
        vector<int> v2;

        unordered_map<char, int> mp1;
        unordered_map<char, int> mp2;

    

        for (int i = 0; i<word1.size(); i++){
            mp1[word1[i]]++;
            mp2[word2[i]]++;
        }

        for (auto it:mp1){
            if(mp2.find(it.first)== mp2.end()) {  //check if the map has the same 'keys'
                return false; 
                } 
        }

        for (auto it:mp1){
            v1.push_back(it.second);
        }
        for (auto it:mp2){
            v2.push_back(it.second);
        }

        //sort the vector (to check if both the vectors are same)
        sort (v1.begin(), v1.end()); 
        sort (v2.begin(), v2.end());

        if (v1==v2){
            return true;
        }
        
        else {
            return false;
        }
}
int main() {
    string word1 = "abc";
    string word2 = "bca";

    if (closeStrings(word1, word2)) {
        cout << "true" << endl;
    } else {
        cout << "false" << endl;
    }

    return 0;
}
