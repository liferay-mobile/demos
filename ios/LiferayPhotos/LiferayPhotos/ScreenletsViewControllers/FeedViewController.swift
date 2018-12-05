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

class FeedViewController: XibViewController, ImageGalleryScreenletDelegate {

	@IBOutlet weak var screenlet: ImageGalleryScreenlet!

	override func viewDidLoad() {
		super.viewDidLoad()
		screenlet.delegate = self
		screenlet.folderId = 72155
		screenlet.repositoryId = 20143
	}

	override func viewDidAppear(_ animated: Bool) {

	}

	private func showLogin(){
		if !SessionContext.isLoggedIn {
			self.performSegue(withIdentifier: "LoginViewController", sender: nil)
		}
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageEntriesResponse imageEntries: [ImageEntry]) {

	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageEntriesError error: NSError) {
		self.showAlert(title: "Error")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageEntrySelected imageEntry: ImageEntry) {
		print("item selected")
        let viewController = DetailViewController(imageClassPK: imageEntry.imageEntryId)
        self.navigationController?.pushViewController(viewController, animated: true)
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageEntryDeleted imageEntry: ImageEntry) {
		self.showAlert(title: "Image deleted")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageEntryDeleteError error: NSError) {
		self.showAlert(title: "Error deleting image")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadStart imageEntryUpload: ImageEntryUpload) {
		print("image prepared to be upload")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet,
				   onImageUploadProgress imageEntryUpload: ImageEntryUpload,
				   totalBytesSent: UInt64,
				   totalBytesToSend: UInt64) {
		print("Upload progress change")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadError error: NSError) {
		self.showAlert(title: "An error occurs in the upload process")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploaded image: ImageEntry) {
		self.showAlert(title: "Image upload finished")
	}

	func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploadDetailViewCreated view: ImageUploadDetailViewBase) -> Bool {
		print("Image upload viw is instantiated")
		return true
	}
}
