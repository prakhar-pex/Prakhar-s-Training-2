#include <bits/stdc++.h>
using namespace std;

struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(nullptr) {}
};

class Solution {
public:
    void reorderList(ListNode* head) {
        ListNode* slow = head;
        ListNode* fast = head;


//finding mid 
        while (fast != NULL && fast->next != NULL) {
            slow = slow->next;
            fast = fast->next->next;
        }

        // reverse list for the SECOND half. from mid to end of list. 
        ListNode* temp = slow->next;
        ListNode* prev = NULL;
        while (temp != NULL) {
            ListNode* front = temp->next;
            temp->next = prev;
            prev = temp;
            temp = front;
        }


        //break this link of mid, and point it to null
        slow->next = NULL;
        ListNode* first = head;
        ListNode* sec = prev;
        // merge and reverse the link alternately. 
        while (sec) {
            ListNode* temp1 = first->next;
            ListNode* temp2 = sec->next;

            first->next = sec;
            sec->next = temp1;

            first = temp1;
            sec = temp2;
        }
    }
};

int main() {
  
    ListNode* head = new ListNode(1);
    head->next = new ListNode(2);
    head->next->next = new ListNode(3);
    head->next->next->next = new ListNode(4);
    head->next->next->next->next = new ListNode(5);

    Solution sol;
    sol.reorderList(head);

    ListNode* curr = head;
    while (curr) {
        cout << curr->val << " ";
        curr = curr->next;
    }
    cout << "\n";

    return 0;
}
