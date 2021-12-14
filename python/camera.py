import cv2
import os
import numpy as np

diractory_path = 'C:\\temp\\'

def createFolder(directory):
    try:
        if not os.path.exists(directory):
            os.makedirs(directory)
    except OSError:
        print ('Error: Creating directory. ' +  directory)

def capture() :    
    cap_side = cv2.VideoCapture(0)
    cap_vertical = cv2.VideoCapture(1)

    if cap_side.isOpened() and cap_vertical.isOpened() :
        while True: 
            img_side_ret, img_side_frame = cap_side.read()
            img_vertical_ret, img_vertical_frame = cap_vertical.read()

            if img_side_ret and img_vertical_ret :
                createFolder(diractory_path)
                cv2.imwrite(diractory_path + 'crop_side_image.png', img_side_frame)
                cv2.imwrite(diractory_path + 'crop_vetical_image.png', img_vertical_frame)
                
                cap_side.release()
                cap_vertical.release()
                break
                
            else : 
                print('생장률을 측정할 수 없습니다.')
                break
    else:
        print('no camera!')
    
    images = [img_side_frame, img_vertical_frame]

    return images

def analysis() :
    img_side_frame, img_vertical_frame = capture()  

    roi_side = img_side_frame[50:400, 50:550]
    roi_vertical = img_vertical_frame[50:400, 50:550]

    img_side = cv2.cvtColor(roi_side, cv2.COLOR_BGR2HSV)
    img_vertical = cv2.cvtColor(roi_vertical, cv2.COLOR_BGR2HSV)

    cv2.imshow("side_img",roi_side)
    cv2.imshow("vertical_img", roi_vertical)

    cv2.waitKey(0)

    lower_green = (7, 0, 15)
    upper_green = (120, 255, 205)

    img_mask_side = cv2.inRange(roi_side, lower_green, upper_green)
    img_mask_vertical = cv2.inRange(roi_vertical, lower_green, upper_green)

    img_result_side = cv2.bitwise_and(roi_side, roi_side, mask=img_mask_side)
    img_result_vertical = cv2.bitwise_and(roi_vertical, roi_vertical, mask=img_mask_vertical)

    cv2.imshow("side_img2",img_result_side)
    cv2.imshow("vertical_img2", img_result_vertical)

    cv2.waitKey(0)


    img_side = cv2.cvtColor(roi_side, cv2.COLOR_HSV2BGR)
    img_vertical = cv2.cvtColor(roi_vertical, cv2.COLOR_HSV2BGR)

    img_B_side, img_G_side, img_R_side = cv2.split(img_side)
    img_B_vertical, img_G_vertical, img_R_vertical = cv2.split(img_vertical)

    side_analysis = (str(round(np.mean(img_R_side))) + ',' + str(round(np.mean(img_G_side))) + ',' + str(round(np.mean(img_B_side))))
    vertical_analysis = (str(round(np.mean(img_R_vertical))) + ',' + str(round(np.mean(img_G_vertical))) + ',' + str(round(np.mean(img_B_vertical))))

    crop_analysis = [side_analysis, vertical_analysis]

    return crop_analysis

print(analysis()) 