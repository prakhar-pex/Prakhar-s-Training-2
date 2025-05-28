#include <bits/stdc++.h>
using namespace std;

// Definition for singly-linked list
struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(nullptr) {}
};

class Solution {
public:


//reverse to get the correct answer. 
ListNode* reverseList(ListNode* head){
        ListNode* prev = NULL;
        ListNode* temp = head;
        while (temp != NULL) {
            ListNode* nextNode = temp->next;
            temp->next = prev;
            prev = temp;
            temp = nextNode;
        }
        return prev;
    }
    ListNode* addTwoNumbers(ListNode* head1, ListNode* head2) {
        ListNode* dHead = new ListNode(-1);
        ListNode* curr = dHead;
        ListNode* temp1 = head1;
        ListNode* temp2 = head2; 
        
        int carry = 0; 
        
        while (temp1 != NULL || temp2 != NULL) {
            int sum = carry; 
            
            if (temp1 != NULL) sum += temp1->val;
            if (temp2 != NULL) sum += temp2->val;
            
            ListNode* newNode = new ListNode(sum % 10); // storing the remainder
            carry = sum / 10; // store the quotient 

            // connecting with the newly created node
            curr->next = newNode;
            
            // move curr to the next , cuz can make a new node after this curr.
            curr = curr->next;

            if (temp1 != NULL) temp1 = temp1->next;
            if (temp2 != NULL) temp2 = temp2->next;
        }

        if (carry) {
            // create and attach new nodes if there is still a carry
            ListNode* newNode = new ListNode(carry);
            
            //link the node on which we were to the newly created node for carry.
            curr->next = newNode; 
        }
        
        return reverseList(dHead->next);
        
    }   
};


int main() {
 
    ListNode* l1 = new ListNode(9);
    l1->next = new ListNode(9);
    l1->next->next = new ListNode(9);


    ListNode* l2 = new ListNode(9);
    l2->next = new ListNode(9);
    l2->next->next = new ListNode(9);

    Solution s;
    ListNode* res = s.addTwoNumbers(l1, l2);

    while (res) {
        cout << res->val << " ";
        res = res->next;
    }
    return 0;
}


// O/P: 1 9 9 8

