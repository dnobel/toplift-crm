package org.nobel.highriseandroid.util;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.nobel.highriseapi.entities.base.EntityWithAvatarUrl;
import org.nobel.highriseapi.entities.base.EntityWithImage;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class ImageUtil {

	public static Drawable createImageFromUrl(String urlPath) {
		Object content = null;
		try {
			URL url = new URL(urlPath);
			URLConnection connection = url.openConnection();
			connection.setUseCaches(true);
			content = connection.getContent();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		InputStream is = (InputStream) content;
		Drawable image = Drawable.createFromStream(is, "src");
		return image;
	}

	public static void setImage(Activity activity, ImageView imageView, EntityWithImage entityWithImage) {

		AndroidEntityImage image = (AndroidEntityImage) entityWithImage.getImage();

		if (image != null) {
			Drawable drawable = image.getDrawable();
			imageView.setImageDrawable(drawable);
		} else {
			new LoadImageTask(activity).execute(imageView, ((EntityWithAvatarUrl) entityWithImage).getAvatarUrl(),
					entityWithImage);
		}
	}

}
