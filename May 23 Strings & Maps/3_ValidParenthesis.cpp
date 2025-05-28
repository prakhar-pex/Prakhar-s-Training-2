#include <bits/stdc++.h>
using namespace std;

bool isValid(string s) {
        stack <char> st; 

        for (int i =0; i<s.length(); i++){
            if (s[i]=='(' || s[i]=='[' || s[i]=='{')
            st.push(s[i]);
            else {
                if (!st.empty()){
                    if (st.top()=='(' && s[i] ==')' || st.top() == '{' && s[i]=='}' || st.top()== '[' && s[i]==']'){
                    st.pop();
                }
                else return false; //if no pattern exsit
                }
                else return false; //if nothing in stack.- if the stack is empty when you encounter a closing bracket, it’s invalid — so return false.
                
            }
           
        }
        if (st.size()==0){
        
        return true;    
        }
        return false; 
}

int main() {
    string s = "{[()]}"; 
    if (isValid(s)) {
        cout << "Valid" << endl;
    } else {
        cout << "Invalid" << endl;
    }

    return 0;
}
