#include <bits/stdc++.h>
#include <vector>
using namespace std;

int subarraySum(vector<int>& nums, int k) {
    int c = 0;

    for (int i = 0; i < nums.size(); i++) {
        int sum = 0;
        for (int j = i; j < nums.size(); j++) {
            sum += nums[j];
            if (sum == k) c++;
        }
    }

    return c;
}

int main() {
    vector<int> nums = {1, 1, 1};
    int k = 2;

    int result = subarraySum(nums, k);
    cout << result << endl;

    return 0;
}
