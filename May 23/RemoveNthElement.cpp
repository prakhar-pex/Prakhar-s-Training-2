#include <bits/stdc++.h>
using namespace std;

struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(nullptr) {}
};

ListNode* removeNthFromEnd(ListNode* head, int n) {
    ListNode* fast = head;
    ListNode* slow = head;

    // Move fast pointer n steps ahead
    // This creates a gap of n between fast and slow
    for (int i = 0; i < n; i++) {
        fast = fast->next;
    }

    // If fast becomes NULL, the Nth node from the end is the head
    // we need to remove the head
    if (fast == NULL)
        return head->next;

    // Move both fast and slow until fast reaches last node
    // Now slow is just before the node to be deleted
    while (fast->next != NULL) {
        fast = fast->next;
        slow = slow->next;
    }

    slow->next = slow->next->next;

    return head;
}

void printList(ListNode* head) {
    while (head != nullptr) {
        cout << head->val << " ";
        head = head->next;
    }
    cout << endl;
}

ListNode* createList(int arr[], int size) {
    if (size == 0) return nullptr;
    ListNode* head = new ListNode(arr[0]);
    ListNode* current = head;
    for (int i = 1; i < size; i++) {
        current->next = new ListNode(arr[i]);
        current = current->next;
    }
    return head;
}

int main() {
    int arr[] = {1, 2, 3, 4, 5};
    int n = 2;
    int size = sizeof(arr) / sizeof(arr[0]);

    ListNode* head = createList(arr, size);


    head = removeNthFromEnd(head, n);


    printList(head);

    return 0;
}
