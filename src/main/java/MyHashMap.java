class MyHashMap<K,V> {

    Node<K, V> buckets[];
    int size;
    final int capacity;

    public MyHashMap() {
        capacity = 11;
        buckets = new Node[capacity];
        size = 0;
    }

    public MyHashMap(int capacity) {
        this.capacity = capacity;
        buckets = new Node[capacity];
        size = 0;
    }

    public void put(K key, V value) {
        int index = Math.abs(key.hashCode() % capacity);


        if(buckets[index] == null){
            buckets[index] = new Node<K, V>(key, value);
        }
        else{
            Node<K, V> temp;
            temp = buckets[index];

            if(buckets[index].key.equals(key)){
                return;
            }

            while(temp.next != null){

                if(temp.key.equals(key)){
                    return;
                }

                temp = temp.next;
            }
            temp.next = new Node<K, V>(key, value);
        }
        size++;

    }

    public void remove(K key) {
        int index = key.hashCode() % capacity;

        if(buckets[index] == null){
            return;
        }
        else if(buckets[index].key.equals(key)){
            buckets[index] = buckets[index].next;
            size--;
        }
        else{
            Node<K, V> temp;
            temp = buckets[index];

            while(temp.next != null && !temp.next.key.equals(key)){
                temp = temp.next;
            }

            if(temp.next == null){
                return;
            }

            if(temp.next.key.equals(key)){
                System.out.println("Key Found");
                if(temp.next.next != null){
                    temp = temp.next.next;
                }else{
                    temp.next = null;
                }
                size--;
            }
        }
    }


    public V get(K key) {

        int index = key.hashCode() % capacity;

        if(buckets[index] == null){
            return null;
        }
        else{
            Node<K, V> temp;
            temp = buckets[index];

            while(!temp.key.equals(key) && temp.next != null){
                temp = temp.next;
            }
            if(temp.key.equals(key)) {
                return temp.data;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (Node<K,V> n : buckets){
            if(n != null){
                Node<K,V> n3 = n;
                do {
                    result.append(n3.toString() + System.lineSeparator());
                    n3 = n3.next;
                } while (n3 != null);
            }
        }
        return result.toString();
    }

    private class Node<K, V> {
        K key;
        V data;
        Node<K, V> next;

        public Node(K key, V data) {
            this.key = key;
            this.data = data;
            this.next = null;
        }

        public String toString(){
            return "Key: " + this.key.toString() + System.lineSeparator() + "Data: " + this.data.toString();
        }
    }
}


