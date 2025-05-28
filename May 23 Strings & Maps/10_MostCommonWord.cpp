#include <bits/stdc++.h>
using namespace std;

string mostCommonWord(string paragraph, vector<string> &banned)
{

    for (int i = 0; i < paragraph.size(); i++)
    {
        char c = paragraph[i];
        if (c == '!' || c == '?' || c == ',' || c == ';' || c == '.')
        {
            paragraph[i] = ' ';
        }
    }
    int n = paragraph.size();
    string word = "";
    vector<string> arr;
    map<string, int> mp1;

    // APPROACH 1- TO GET EACH WORD //break string into single characters and build the word
    // for (int i = 0; i < n; i++) {
    //     if (isalpha(paragraph[i])) {
    //         word += tolower(paragraph[i]);
    //     } else if (paragraph[i] == ' ' && !word.empty()) {
    //         arr.push_back(word); // add completed word to array
    //         word = "";
    //     }
    // }
    // if (!word.empty()) arr.push_back(word);  // push last word (in case only single word is present)

    // APPROACH 2 - use stringstream

    // Use stringstream to split words by spaces
    stringstream ss(paragraph);
    while (ss >> word)
    {
        string temp = "";
        for (char ch : word)
        {
            temp += tolower(ch); // convert each character to lowercase
        }
        arr.push_back(temp); // add the processed word
    }

    
    // count frequency of each word
    for (int i = 0; i < arr.size(); i++)
    {
        mp1[arr[i]]++;
    }

    // remove banned words from the map
    for (int i = 0; i < banned.size(); i++)
    {
        mp1.erase(banned[i]);
    }

    string ans = "";
    int maxFreq = 0;

    // find word with maximum frequency
    for (auto it : mp1)
    {
        if (it.second > maxFreq)
        {
            maxFreq = it.second;
            ans = it.first;
        }
    }

    return ans;
}
int main()
{
    string paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.";
    vector<string> banned = {"hit"};

    string result = mostCommonWord(paragraph, banned);
    cout << result << endl;

    return 0;
}
