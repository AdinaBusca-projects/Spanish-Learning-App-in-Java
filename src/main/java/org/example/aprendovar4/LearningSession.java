package org.example.aprendovar4;

public class LearningSession implements Learnable{
    private String name;
    private String content;

    public LearningSession(){

    }

    public LearningSession(String name, String content){
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void startLearning() {
        if (content == null || content.isEmpty()){
            System.out.println("No content available for this learning session");
            return;
        }

        //split content into sentences by using regex
        String[] sentences = content.split("(?<=[.!?])\\s+");

        //display three sentences per line
        for (int i = 0; i < sentences.length; i++){
            System.out.print(sentences[i].trim());

            if((i+1) % 3 == 0 || i == sentences.length -1){
                System.out.println();//print newline
            }else{
                System.out.print(" ");//print empty space between two sentences
            }
        }

    }
}