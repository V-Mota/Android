import pyautogui
import pytesseract

import json
import time

from pynput.mouse import Button, Controller
from heapq import nlargest

mouse = Controller()

# area_pergunta = (1440, 120, 1890, 620)
# area_r1 = (1440, 700, 1890, 755)
# area_r2 = (1440, 765, 1890, 825)
# area_r3 = (1440, 835, 1890, 890)
# area_r4 = (1440, 900, 1890, 955)
#
# ajuda_location = (1490, 665)
# r1_location = (1490, 730)
# r2_location = (1490, 795)
# r3_location = (1490, 865)
# r4_location = (1490, 930)

area_pergunta = (1000, 250, 1890, 650)
area_r1 = (970,  850, 1900,  890)
area_r2 = (970,  900, 1900,  940)
area_r3 = (970,  950, 1900,  990)
area_r4 = (970, 1000, 1900, 1040)

ajuda_location = (980, 820)
r1_location = (980,  860)
r2_location = (980,  910)
r3_location = (980,  960)
r4_location = (980, 1010)



r_locations = [r1_location, r2_location, r3_location, r4_location]

json_file = '/home/vmota/scripts/quiz.json'


def sleep(t):
    time.sleep(t)

def click(pos):
    # mouse.position = (5, 1075)
    mouse.position = pos
    # Click the left button
    mouse.click(Button.left, 5)
    sleep(0.05)
    mouse.click(Button.left, 1)


def resposta_aleatoria():
    return randrange(4)

def read_json():
    with open(json_file) as infile:
        data = json.load(infile)
    return data

def write_json(data):
    with open(json_file, 'w') as outfile:
        json.dump(data, outfile)


def main():
    ajudas = 1

    data = read_json()

    # click(r4_location)
    # sleep(2)
    # click(r4_location)
    # sleep(5)

    for _ in range(2):

        myScreenshot = pyautogui.screenshot()

        pergunta_img = myScreenshot.crop(area_pergunta)
        r1_img = myScreenshot.crop(area_r1)
        r2_img = myScreenshot.crop(area_r2)
        r3_img = myScreenshot.crop(area_r3)
        r4_img = myScreenshot.crop(area_r4)

        pergunta = pytesseract.image_to_string(pergunta_img)
        r1 = pytesseract.image_to_string(r1_img)
        r2 = pytesseract.image_to_string(r2_img)
        r3 = pytesseract.image_to_string(r3_img)
        r4 = pytesseract.image_to_string(r4_img)
        respostas = [r1, r2, r3, r4]

        print(pergunta)
        print("1 - {}".format(r1))
        print("2 - {}".format(r2))
        print("3 - {}".format(r3))
        print("4 - {}".format(r4))

        if pergunta in data:
            resposta_string = data[pergunta]["certa"]
            print("I know the answer: {}".format(resposta_string))
            if resposta_string == r1:
                click(r1_location)

            elif resposta_string == r2:
                click(r2_location)

            elif resposta_string == r3:
                click(r3_location)

            elif resposta_string == r4:
                click(r4_location)

            else:
                print("none of the answers match expected righ answer")
                break


        elif ajudas > 0:
            ajudas = 0
            click(ajuda_location)
            sleep(1.8)
            myScreenshot = pyautogui.screenshot()
            pixels = myScreenshot.load()
            r1, g1, b1 = pixels[r1_location[0], r1_location[1]]
            r2, g2, b2 = pixels[r2_location[0], r2_location[1]]
            r3, g3, b3 = pixels[r3_location[0], r3_location[1]]
            r4, g4, b4 = pixels[r4_location[0], r4_location[1]]

            print("R1 - R: {} - G: {} - B: {}".format(r1, g1, b1))
            print("R2 - R: {} - G: {} - B: {}".format(r2, g2, b2))
            print("R3 - R: {} - G: {} - B: {}".format(r3, g3, b3))
            print("R4 - R: {} - G: {} - B: {}".format(r4, g4, b4))

            index_certo = 0;
            greenest = 0;
            for i, g in enumerate([g1, g2, g3, g4]):
                if g > greenest:
                    greenest = g
                    index_certo = i


            resposta_certa = respostas[index_certo]
            data.update({pergunta: {"certa": resposta_certa}})
            print("Usei ajuda: {}".format(resposta_certa))

        else:
            # r = resposta_aleatoria
            # click(r_locations[r])
            click(r1_location)

        sleep(3)
        print("############################")

    #end of for loop
    write_json(data)

if __name__ == "__main__":
    main()

#Escolha

# time.sleep(1.51)
# myScreenshot = pyautogui.screenshot()
# pixels = myScreenshot.load()
# R,G,B = pixels[1490, 730]
# #(9, 12, 15)
# if G > R and G > B:
#     #right
#     pass
# elif R > B and R > G:
#     #wrong
#     pass
