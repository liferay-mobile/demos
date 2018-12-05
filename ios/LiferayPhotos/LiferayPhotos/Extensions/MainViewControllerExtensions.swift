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

import Foundation
import iOSPhotoEditor
import LiferayScreens

extension MainViewController: PhotoEditorDelegate {
    
    func showMediaSelectorAndPhotoEditor() {
        if let viewController = emptyGalleryScreenlet.presentingViewController {
            
            let takeNewPicture = LocalizedString("default",
                                                 key: "userportrait-take-new-picture",
                                                 obj: self)
            let chooseExisting = LocalizedString("default",
                                                 key: "userportrait-choose-existing-picture",
                                                 obj: self)
            
            let cancelText = LocalizedString("imagegallery-screenlet", key: "cancel", obj: self)
            
            let alert = MediaSelector(
                viewController: viewController,
                types: [
                    .camera: takeNewPicture,
                    .image: chooseExisting
                ],
                cancelMessage: cancelText) { (image, _) in
                    
                    guard let image = image else {
                        return
                    }
                    
                    self.showPhotoEditor(image)
            }
            alert.show()
        }
    }
    
    private func showPhotoEditor(_ image: UIImage) {
        let photoEditor = PhotoEditorViewController(nibName:"PhotoEditorViewController",bundle: Bundle(for: PhotoEditorViewController.self))
        photoEditor.photoEditorDelegate = self
        photoEditor.image = image
        //Colors for drawing and Text, If not set default values will be used
        //photoEditor.colors = [.red, .blue, .green]
        
        //Stickers that the user will choose from to add on the image
        for i in 0...10 {
            photoEditor.stickers.append(UIImage(named: i.description )!)
        }
        
        //To hide controls - array of enum control
        photoEditor.hiddenControls = [.save]
        self.present(photoEditor, animated: true, completion: nil)
    }
    
    // MARK photoEditorProtocol
    
    func doneEditing(image: UIImage) {
        let imageUpload = ImageEntryUpload(image: image, title: "")
        emptyGalleryScreenlet.showDetailUploadView(imageUpload)
    }
    
    func canceledEditing() {
        print("Canceled")
    }
}


