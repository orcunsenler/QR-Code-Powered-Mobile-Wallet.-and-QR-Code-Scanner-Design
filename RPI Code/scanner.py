from tkinter import *
from tkinter import ttk
import tkinter as tk
import tkinter.font
import cv2
import sys
import urllib.request as urllib2


def QRscan():
    
    cap = cv2.VideoCapture(0)

    detector = cv2.QRCodeDetector()

    while True:
        _, img = cap.read()
        dataEncode, bbox, _ = detector.detectAndDecode(img)
    
        if(bbox is not None):
            for i in range(len(bbox)):
                cv2.line(img, tuple(bbox[i][0]), tuple(bbox[(i+1) % len(bbox)][0]), color=(255,
                         0, 255), thickness=2)
            cv2.putText(img, dataEncode, (int(bbox[0][0][0]), int(bbox[0][0][1]) - 10), cv2.FONT_HERSHEY_SIMPLEX,
                    0.5, (0, 255, 0), 2)
            if dataEncode:
                QRcheck(dataEncode)
        cv2.imshow("code detector", img)
        cv2.waitKey(1)
        if(last_code==dataEncode):
            break
    cap.release()
    cv2.destroyAllWindows()
 
last_code = None        
def QRcheck(code):
    global last_code
    global userInfo_1
    global userInfo_2
   
    if code != last_code:
        data = nameCheck(code)
        userInfo = data.split("|")
        userInfo_1 = userInfo[0]
        userInfo_2 = userInfo[1]
        UserInfo(userInfo_1,userInfo_2)
        last_code = code

def UserInfo(name,cash):
    UserName2 = Label(window)
    UserName2['text']= name
    UserName2.grid(row=0, column=1)
    Balance2 = Label(window)
    Balance2['text']= cash
    Balance2.grid(row=1, column=1)

query_url= "http://192.168.43.215/wallet/rpiQuery.php?encrypted="    
def nameCheck(encryptedName):
    response=urllib2.urlopen(query_url+encryptedName).read()
    return response

payment_url = "http://192.168.43.215/wallet/update.php?name=" 
def Payment():
    global amount
    amount = paymentEntry.get()
    response=urllib2.urlopen(payment_url+userInfo_1+"&amount=-"+amount).read()
    encryptedData = nameCheck(last_code)
    jsData = encryptedData.decode('UTF-8')
    Data = jsData.split("|")
    UserInfo(Data[0], Data[1])
     
window  = tk.Tk()
window.title("QR Code Scanner")
window.geometry('400x200')
myFont = tk.font.Font(family = 'Helvetica', size = 12, weight = "bold")

varName = tk.StringVar()
UserName = tk.Label(window,textvariable=varName, font = myFont)
varName.set("Username:")
UserName.grid(row=0, column=0)
varBalance = tk.StringVar()
Balance = tk.Label(window,textvariable=varBalance ,font = myFont)
varBalance.set("Balance:")
Balance.grid(row=1, column=0)

varPayment = tk.StringVar()
paymentLabel = tk.Label(window, textvariable=varPayment)
patmentLabel.grid(row=2, column=0)
varPayment.set("Enter Payment Amount")
paymentEntry = tk.Entry(window, bd=5)
paymentEntry.grid(row=2, column=1)

paymentButton = tk.Button(window, text = "Payment",font = myFont, command = Payment)
paymentButton.grid(row=3, column=1)
scanButton = tk.Button(window, text="Scan", font = myFont,command = QRscan)
scanButton.grid(row=3, column=0)
exitButton = tk.Button(window, text="Exit", font = myFont, command = window.destroy)
exitButton.grid(row=3, column=2)

window.mainloop()

