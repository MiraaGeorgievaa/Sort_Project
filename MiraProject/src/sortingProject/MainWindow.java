package sortingProject;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MainWindow
{

    private JFrame frame;
    JPanel panel = new JPanel();
    private JComboBox comboBox;
    private Object[] objects;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run() 
            {
                try
                {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                } 
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() 
    {
        
        //Objects
        Object objectXS = new Object (1, "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Red_circle.svg/1200px-Red_circle.svg.png");
        Object objectS = new Object (2, "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/Eo_circle_green_blank.svg/1024px-Eo_circle_green_blank.svg.png");
        Object objectM = new Object(3, "https://upload.wikimedia.org/wikipedia/commons/b/b7/Purple_Circle.png");
        Object objectL = new Object(4, "https://em-content.zobj.net/source/google/387/large-orange-circle_1f7e0.png");
        Object objectXL = new Object(5, "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Circle_Seal-brown_Solid.svg/1200px-Circle_Seal-brown_Solid.svg.png");
        
        //Array of objects
        objects = new Object[] {objectL, objectS, objectXL, objectXS, objectM};
        
        //Creating Frame
        frame = new JFrame();
        frame.setBounds(100, 100, 715, 404);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Positions of the 5 objects
        int position1 = 50;
        int position2 = 100;
        int position3 = 150;
        int position4 = 200;
        int position5 = 250;
        
        //Creating a panel
        panel.setVisible(true);
        panel.setLayout(null);
        
        int positionX = 50;
        
        //Creating images
        for (Object obj : objects)
        {
            JLabel label = new JLabel();
            ImageIcon icon = createImageIcon(obj.getImage_url());
            if (icon != null) 
            {
                // Resizing the images
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(obj.getSize() * 20, obj.getSize() * 20, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);

                label.setIcon(icon);
                label.setBounds(positionX, 200, icon.getIconWidth(), icon.getIconHeight());
                panel.add(label);
                positionX += 100;
            }
        }
        
        //Adding the components to the main panel
        frame.getContentPane().add(panel);
        
        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Heap Sort"}));
        comboBox.setBounds(468, 16, 172, 27);
        panel.add(comboBox);
        
        JLabel lblNewLabel = new JLabel("Choose the type of sorting");
        lblNewLabel.setBounds(251, 20, 189, 16);
        panel.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Start Sorting");
        btnNewButton.setBounds(287, 48, 117, 29);
        panel.add(btnNewButton);
        frame.setVisible(true); // Make the frame visible
        
        btnNewButton.addActionListener(e -> {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception
                {
                    String selectedSort = comboBox.getSelectedItem().toString();
                    switch(selectedSort)
                    {
                        case "Bubble Sort":
                            bubbleSort(objects);
                            break;
                        case "Selection Sort":
                            selectionSort(objects);
                            break;
                        case "Insertion Sort":
                            insertionSort(objects);
                            break;
                        case "Merge Sort":
                            mergeSort(objects, 0, objects.length - 1);
                            break;
                        case "Quick Sort":
                            quickSort(objects, 0, objects.length - 1);
                            break;
                        case "Heap Sort":
                            heapSort(objects);
                            break;
                    }
                    return null;
                }
            };
            worker.execute();
        });
        
    }
    
    //Creating image object from the url
    private ImageIcon createImageIcon(String url) 
    {
        try 
        {
            URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedImage img = ImageIO.read(connection.getInputStream());
            return new ImageIcon(img);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    // Bubble Sort
    private void bubbleSort(Object[] objects)
    {
        System.out.println("Starting bubble sort");
        int n = objects.length;
        for (int i = 0; i < n - 1; i++)
        {
            for (int j = 0; j < n - i - 1; j++)
            {
                if (objects[j].getSize() > objects[j + 1].getSize())
                {
                    // Swap arr[j] and arr[j+1]
                    Object temp = objects[j];
                    objects[j] = objects[j + 1];
                    objects[j + 1] = temp;

                    System.out.println("swapping");
                    // Update GUI after each swap
                    drawCircles(objects);
                    try
                    {
                        Thread.sleep(500); // Delay for visualization
                    }
                    catch (InterruptedException exc) 
                    {
                        exc.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Sorted");
        showFinishMessage("Bubble Sort");
    }
    
    // Selection Sort
    private void selectionSort(Object[] objects) 
    {
        System.out.println("Starting selection sort");
        int n = objects.length;
        for (int i = 0; i < n - 1; i++)
        {
            int minIndex = i;
            for (int j = i + 1; j < n; j++)
            {
                if (objects[j].getSize() < objects[minIndex].getSize()) 
                {
                    minIndex = j;
                }
            }
            // Swap arr[minIndex] and arr[i]
            Object temp = objects[minIndex];
            objects[minIndex] = objects[i];
            objects[i] = temp;

            System.out.println("swapping");
            // Update GUI after each swap
            drawCircles(objects);
            try 
            {
                Thread.sleep(500); // Delay for visualization
            } 
            catch (InterruptedException exc)
            {
                exc.printStackTrace();
            }
        }
        System.out.println("Sorted");
        showFinishMessage("Selection Sort");
    }
    
    // Insertion Sort
    private void insertionSort(Object[] objects)
    {
        System.out.println("Starting insertion sort");
        int n = objects.length;
        for (int i = 1; i < n; ++i) 
        {
            Object key = objects[i];
            int j = i - 1;

            while (j >= 0 && objects[j].getSize() > key.getSize()) 
            {
                objects[j + 1] = objects[j];
                j = j - 1;
            }
            objects[j + 1] = key;

            System.out.println("swapping");
            // Update GUI after each swap
            drawCircles(objects);
            try 
            {
            	// Delay for visualization
                Thread.sleep(500);
            } 
            catch (InterruptedException exc) 
            {
                exc.printStackTrace();
            }
        }
        System.out.println("Sorted");
        showFinishMessage("Insertion Sort");
    }
    
    // Merge Sort
    private void mergeSort(Object[] objects, int l, int r) 
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            mergeSort(objects, l, m);
            mergeSort(objects, m + 1, r);

            // Merge the sorted halves
            merge(objects, l, m, r);
            
            System.out.println("swapping");
            // Update GUI after each swap
            drawCircles(objects);
            try 
            {
            	// Delay for visualization
                Thread.sleep(500);
            } 
            catch (InterruptedException exc)
            {
                exc.printStackTrace();
            }
        }
    }
    
    private void merge(Object[] objects, int l, int m, int r) 
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temporary arrays */
        Object[] L = new Object[n1];
        Object[] R = new Object[n2];

        /*Copy data to temporary arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = objects[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = objects[m + 1 + j];

        /* Merge the temporary arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i].getSize() <= R[j].getSize()) {
                objects[k] = L[i];
                i++;
            } 
            else
            {
                objects[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        {
            objects[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) 
        {
            objects[k] = R[j];
            j++;
            k++;
        }
    }
    
    // Quick Sort
    private void quickSort(Object[] objects, int low, int high) {
        if (low < high) {
            /* pi is partitioning index, arr[pi] is now
               at right place */
            int pi = partition(objects, low, high);

            // Recursively sort elements before
            // partition and after partition
            quickSort(objects, low, pi - 1);
            quickSort(objects, pi + 1, high);
            
            System.out.println("swapping");
            // Update GUI after each swap
            drawCircles(objects);
            try 
            {
                Thread.sleep(500); // Delay for visualization
            } 
            catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }
    
    private int partition(Object[] objects, int low, int high) 
    {
        Object pivot = objects[high];
        int i = (low - 1); // index of smaller element
        for (int j = low; j < high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (objects[j].getSize() <= pivot.getSize())
            {
                i++;

                // swap arr[i] and arr[j]
                Object temp = objects[i];
                objects[i] = objects[j];
                objects[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        Object temp = objects[i + 1];
        objects[i + 1] = objects[high];
        objects[high] = temp;

        return i + 1;
    }
    
    // Heap Sort
    private void heapSort(Object[] objects) 
    {
        int n = objects.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(objects, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--)
        {
            // Move current root to end
            Object temp = objects[0];
            objects[0] = objects[i];
            objects[i] = temp;

            // call max heapify on the reduced heap
            heapify(objects, i, 0);
            
            System.out.println("swapping");
            // Update GUI after each swap
            drawCircles(objects);
            try 
            {
                Thread.sleep(500); // Delay for visualization
            } 
            catch (InterruptedException exc) 
            {
                exc.printStackTrace();
            }
        }
        System.out.println("Sorted");
        showFinishMessage("Heap Sort");
    }
    
    // To heapify a subtree rooted with node i which is
    // an index in objects[]. n is size of heap
    private void heapify(Object[] objects, int n, int i)
    {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && objects[l].getSize() > objects[largest].getSize())
            largest = l;

        // If right child is larger than largest so far
        if (r < n && objects[r].getSize() > objects[largest].getSize())
            largest = r;

        // If largest is not root
        if (largest != i) 
        {
            Object swap = objects[i];
            objects[i] = objects[largest];
            objects[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(objects, n, largest);
        }
    }
    
    private void drawCircles(Object[] objects) 
    {
        // Clear the panel before adding new circles
        panel.removeAll();
        
        int positionX = 50;
        for (Object obj : objects) 
        {
            JLabel label = new JLabel();
            ImageIcon icon = createImageIcon(obj.getImage_url());
            if (icon != null) 
            {
                // Resizing the images
                Image img = icon.getImage();
                Image newImg = img.getScaledInstance(obj.getSize() * 20, obj.getSize() * 20, Image.SCALE_SMOOTH);
                icon = new ImageIcon(newImg);

                label.setIcon(icon);
                label.setBounds(positionX, 200, icon.getIconWidth(), icon.getIconHeight());
                panel.add(label);
                positionX += 100;
            }
        }
        
        // Repaint the panel to reflect the changes
        panel.revalidate();
        panel.repaint();
    }
    
    private void showFinishMessage(String sortType)
    {
        JLabel finishLabel = new JLabel("You successfully sorted the objects using " + sortType);
        finishLabel.setBounds(70, 20, 400, 16);
        panel.add(finishLabel);
        panel.revalidate();
        panel.repaint();
    }
    
}
