import random
vowels = "aeiou"
consonants ="bcdfghjklmnpqrstvwxyz"

def createName():
    n = random.randint(3, 20)
    name = ""
    firstLetter = random.randint(0,len(consonants)-1)
    name += consonants[firstLetter].upper()
    for x in range(1,n):
        v = random.randint(0, 2)
        if v == 0:
            name += vowels[random.randint(0,len(vowels)-1)]
        else:
            name += consonants[random.randint(0,len(consonants)-1)]
    return name






def generate_names(file, nNames):
    f = open("./passengers/" + file, "w")
    for x in range(nNames):
        f.write(createName() + "\n")
    f.close()




if __name__ == '__main__':
    generate_names("last-names.txt",10)
    generate_names("mother-names.txt",10)
    generate_names("non-man.txt",10)
    generate_names("non-woman.txt",10)



