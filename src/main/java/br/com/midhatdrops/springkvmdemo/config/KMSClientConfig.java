package br.com.midhatdrops.springkvmdemo.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KMSClientConfig {

    @Value("${aws.credential.access-key}")
    private String accessKey;

    @Value("${aws.credential.secret-key}")
    private String secretKey;


    @Bean
  public AWSKMS generateKMSClient() {


        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey,this.secretKey));

        return AWSKMSClientBuilder.standard().withCredentials(awsStaticCredentialsProvider).build();

  }

}
