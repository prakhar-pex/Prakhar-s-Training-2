package BridgeCrossing;

class Bridge {
    private final Object lock = new Object();
    private boolean isTokenInCityB = true; // Token starts in City B

    public void cross(String personName, char fromCity) throws InterruptedException {
        synchronized (lock) {
            while ((fromCity == 'A' && isTokenInCityB) || (fromCity == 'B' && !isTokenInCityB)) {
                lock.wait(); // Wait if token is not on your side
            }

            // Cross the bridge
            System.out.println(personName + " crossing from City " + fromCity +
                    " to City " + (fromCity == 'A' ? 'B' : 'A'));

            Thread.sleep(500); // Simulate crossing time

            isTokenInCityB = !isTokenInCityB; // Move token to other side
            lock.notifyAll(); // Wake up others
        }
    }
}

public class BridgeCrossing {
    public static void main(String[] args) {
        Bridge bridge = new Bridge();

        Runnable person1 = () -> {
            try {
                bridge.cross("Person1", 'B');
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable person2 = () -> {
            try {
                bridge.cross("Person2", 'B');
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable person3 = () -> {
            try {
                bridge.cross("Person3", 'A');
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable person4 = () -> {
            try {
                bridge.cross("Person4", 'A');
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // Start threads
        new Thread(person1).start();
        new Thread(person2).start();
        new Thread(person3).start();
        new Thread(person4).start();
    }
}
