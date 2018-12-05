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
import iOSPhotoEditor

class FakeViewController: UIViewController {

}

class MainViewController:
    UITabBarController,
    UITabBarControllerDelegate,
    ImageGalleryScreenletDelegate {

    let emptyGalleryScreenlet = ImageGalleryScreenlet(frame: .zero, themeName: "liferayphotos")
    
	override func viewDidLoad() {
		super.viewDidLoad()
        
		tabBar.tintColor = .black
        delegate = self

        emptyGalleryScreenlet.presentingViewController = self
        emptyGalleryScreenlet.folderId = 72155
        emptyGalleryScreenlet.repositoryId = 20143
        emptyGalleryScreenlet.delegate = self
        
        
		let feedVC = FeedViewController()
		let feedTabBarItem = UITabBarItem(
			title: nil,
			image: UIImage(named: "feed_disabled"),
			selectedImage: UIImage(named: "feed_enabled"))

		feedTabBarItem.imageInsets = UIEdgeInsets(top: 6, left: 0, bottom: -6, right: 0)
		feedVC.tabBarItem = feedTabBarItem
        
        let cameraVC = FakeViewController()
        let cameraTabBarItem = UITabBarItem(title: nil, image: UIImage(named: "plus-button"), selectedImage: UIImage(named: "plus-button"))
        cameraTabBarItem.imageInsets = UIEdgeInsets(top: 6, left: 0, bottom: -6, right: 0)
        cameraVC.tabBarItem = cameraTabBarItem

		let userProfileVC = UserProfileViewController()

		let profileTabBarItem = UITabBarItem(
			title: nil,
			image: UIImage(named: "profile_disabled"),
			selectedImage: UIImage(named: "profile_enabled"))

		profileTabBarItem.imageInsets = UIEdgeInsets(top: 6, left: 0, bottom: -6, right: 0)

		userProfileVC.tabBarItem = profileTabBarItem

		viewControllers = [feedVC, cameraVC ,userProfileVC]

		let chatButton = UIBarButtonItem(image: UIImage(named: "chat"), style: .plain, target: self, action: #selector(goToChat))
		navigationItem.rightBarButtonItem = chatButton

		addLogo()
	}

	@objc func goToChat() {
		let chatVC = ChatViewController()
		self.navigationController?.pushViewController(chatVC, animated: true)
	}

	fileprivate func addLogo() {
		let imageView = UIImageView(frame: CGRect(x: 0, y: 0, width: 38, height: 38))
		imageView.contentMode = .scaleAspectFit
		imageView.clipsToBounds = true
		let image = UIImage(named: "logo.png")
		imageView.image = image
		navigationItem.titleView = imageView
	}
    
    // MARK: ImageGalleryScreenletDelegate methods
    func screenlet(_ screenlet: ImageGalleryScreenlet, onImageUploaded image: ImageEntry) {
        let a = CommentAddInteractor.init(
            className: "com.liferay.document.library.kernel.model.DLFileEntry",
            classPK: image.imageEntryId,
            body: image.description)
        _ = a.start()

        a.onSuccess = {
            print("añadido")
        }

        a.onFailure = { error in
            print("Fallo al añadir el comentatio \(error)")
        }
    }
    
    // MARK
    func tabBarController(_ tabBarController: UITabBarController, shouldSelect viewController: UIViewController) -> Bool {
        if viewController is FakeViewController {
            self.showMediaSelectorAndPhotoEditor()
            return false
        }
        return true
    }
}
