/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import UIKit
import LiferayScreens

class FeedCell: UICollectionViewCell, AssetDisplayScreenletDelegate {

	@IBOutlet weak var ratingScreenlet: RatingScreenlet!
	@IBOutlet weak var image: UIImageView!
	@IBOutlet weak var descriptionLabel: UILabel!
	@IBOutlet weak var timeAgoLabel: UILabel!
	@IBOutlet weak var assetDisplayScreenlet: AssetDisplayScreenlet!
	var zoomView: UIImageView!

    override func awakeFromNib() {
        super.awakeFromNib()

		backgroundColor = .white
		layer.cornerRadius = 3

		assetDisplayScreenlet.delegate = self
		assetDisplayScreenlet.autoLoad = false
		ratingScreenlet.autoLoad = false

		let pinch = UIPinchGestureRecognizer(target: self, action: #selector(handlePinch))
		image.isUserInteractionEnabled = true

		image.addGestureRecognizer(pinch)
    }

	@objc func handlePinch(recognizer: UIPinchGestureRecognizer) {

		if recognizer.state == .began {
			var newFrame = superview!.convert(self.frame, to: UIApplication.shared.keyWindow!)
			newFrame.origin.y += 58.0
			newFrame.size = CGSize(width: self.image.frame.width, height: self.image.frame.height)

			zoomView = UIImageView(frame: newFrame)
			zoomView.clipsToBounds = true
			zoomView.contentMode = .scaleAspectFill
			zoomView.image = image.image
			zoomView.backgroundColor = .red

			image.isHidden = true

			UIApplication.shared.keyWindow?.addSubview(zoomView)
		}

		if recognizer.state == .ended  {
			UIView.animate(withDuration: 0.3, animations: {
				self.zoomView.transform = CGAffineTransform.identity
			}, completion: { _ in
				self.zoomView.removeFromSuperview()
				self.image.isHidden = false
			})
		}
		else if recognizer.state == .changed {
			let pinchView = zoomView!
			let bounds = pinchView.bounds

			var pinchCenter = recognizer.location(in: pinchView)
			pinchCenter.x -= bounds.midX
			pinchCenter.y -= bounds.midY

			var transform = pinchView.transform
			let scale = recognizer.scale

			transform = pinchView.transform.translatedBy(x: pinchCenter.x, y: pinchCenter.y)
				.scaledBy(x: scale, y: scale)
				.translatedBy(x: -pinchCenter.x, y: -pinchCenter.y)

			pinchView.transform = transform

			recognizer.scale = 1.0
		}
	}

	func screenlet(_ screenlet: AssetDisplayScreenlet, onAsset asset: Asset) -> UIView? {
		let userView = UserView()
		let object = asset.attributes["object"] as! [String: AnyObject]

		userView.user = User(attributes: object["user"] as! [String: AnyObject])
		return userView
	}

	func screenlet(_ screenlet: AssetDisplayScreenlet, onAssetError error: NSError) {

	}

	func render(imageEntry: ImageEntry) {
		let createDate = Date(millisecondsSince1970: Double(imageEntry.attributes["createDate"]?.int64Value ?? 0))

		image.lr_setImageWithURL(
			URL(string: imageEntry.imageUrl)!,
			placeholderImage: nil,
			optionsInfo: [.backgroundDecode])

		timeAgoLabel.text = createDate.timeAgo

		descriptionLabel.text = imageEntry.attributes["description"] as? String ?? ""

		ratingScreenlet.classPK = imageEntry.attributes["fileEntryId"]?.int64Value ?? 0
		ratingScreenlet.className = "com.liferay.document.library.kernel.model.DLFileEntry"
		ratingScreenlet.loadRatings()

		assetDisplayScreenlet.classPK = imageEntry.attributes["userId"]?.int64Value ?? 0
		assetDisplayScreenlet.className = "com.liferay.portal.kernel.model.User"
		assetDisplayScreenlet.load()
	}
}
