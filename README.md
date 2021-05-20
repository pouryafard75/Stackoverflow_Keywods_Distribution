# Stackoverflow_Keywods_Distribution
 
The project was written in Java and R. 

<i> <b> Output : </b> </i>

<img width="1011" alt="image" src="https://user-images.githubusercontent.com/28820932/119032653-ac91e000-b9c1-11eb-8777-6efec4b0a5f5.png">

Higher quality file is available as "output.pdf".

R code for generating the dendogram:
```
> data1 <- read.csv(file = “DissimilarityTableTop200Tags.csv” , header = TRUE)
> data2 <- data1[,-c(1,1)]
> hc <- hclust (as.dist(data2))
> plot(hc, hang = 0.1, cex = 0.4, xlab = "Tags", ylab = "Dissimilarity")
```
