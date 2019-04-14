#include <ESP8266WiFi.h>

const char* ssid = "Redmi";
const char* password = "rr123456";

// Create an instance of the server
// specify the port to listen on as an argument
WiFiServer server(80);
int ledPin1 = D0,ledPin2=D3,ledPin3=D5,ledPin4=D8;

void setup() {
  Serial.begin(115200);
  delay(10);

  // prepare GPIO2
  pinMode(ledPin1, OUTPUT);
  digitalWrite(ledPin1, 0);

   pinMode(ledPin2, OUTPUT);
  digitalWrite(ledPin2, 0);
   pinMode(ledPin3, OUTPUT);
  digitalWrite(ledPin3, 0);
   pinMode(ledPin4, OUTPUT);
  digitalWrite(ledPin4, 0);
  
  // Connect to WiFi network
  Serial.println();
  Serial.println();
  Serial.println("hello");
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  
  // Start the server
  server.begin();
  Serial.println("here");
  Serial.println("Server started");

  // Print the IP address
  Serial.println(WiFi.localIP());
}
void loop() {
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  
  // Wait until the client sends some data
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
  
  // Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  client.flush();
  
  // Match the request
 
  int i=req[5]-'0';
  int j=req[6]-'0';
  int levels[5]={0,256,512,768,1023};
 // Serial.println(val);
    //Serial.println("invalid request");
  switch(i)
  {
    case 1:analogWrite(ledPin1,levels[j]);
    break;
    case 2:analogWrite(ledPin2,levels[j]);
    break;
    case 3:analogWrite(ledPin3,levels[j]);
    break;
    case 4:analogWrite(ledPin4,levels[j]);
    break;
    
  }
    Serial.println(i,j);
    //return;
    if(i==0)
  client.stop();
  

  
  
  Serial.println("here wrir");
  //digitalWrite(ledPin,1);
 
 client.flush();

   // Return the response
  client.println("HTTP/1.1 200 OK");
  client.println("Content-Type: text/html");
  client.println(""); //  do not forget this one
  client.print("Done: ");
 
  
 // Serial.println("Client disonnected");

  // The client will actually be disconnected 
  // when the function returns and 'client' object is detroyed
}
