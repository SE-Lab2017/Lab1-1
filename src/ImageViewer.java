import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class ImageViewer extends JComponent {

  /**
   *
   */
  private static final long serialVersionUID = 7329313876812878327L;
  final JSlider slider;
  final ImageComponent image;
  final JScrollPane scrollPane;

  public ImageViewer(String path) throws IOException {
    slider = new JSlider(0, 1000, 500);
    image = new ImageComponent(path);
    scrollPane = new JScrollPane(image);

    slider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        image.setZoom(2. * slider.getValue() / slider.getMaximum());
      }
    });

    this.setLayout(new BorderLayout());
    this.add(slider, BorderLayout.NORTH);
    this.add(scrollPane);
  }
}
