The Concurrent Processing Framework (CPF) Client APIs allows web applications to be developed using JavaScript, Java or another programming language to submit batch jobs to the CPF and to download the results of those jobs.

The CPF is a framework for building, deploying and running request/response style web services (business applications).

A business application accepts input parameters, performs some processing using those parameters and then returns the result of the processing.

For example:

* A web map image business application accepts the bounding box, map layers, image size and projection for a map image, it then creates a map image using those parameters and returns the image to the user.
* A geo-coder business application accepts and address and city as parameters, searches the database for the address matches and returns the full address and the point location.

The CPF extends the web service paradigm by adding support for asynchronous processing of a request to a business application and batching of multiple business application requests into a job for asynchronous processing. The asynchronous processing solves the issue of network timeouts/disconnects when waiting for a response for a request. The multiple requests in a single job solves several issues. For the user they only need to submit one request containing all the request parameters rather than thousands or millions of individual requests, this also reduces processing requirements on the clients. For the server/administrator it allows better control over access to the resource as the CPF can distribute the work across multiple worker servers and limit the number of concurrent requests.

The asynchronous processing allows a user to submit the requests for a job with a single web service API call and disconnect from the CPF server. At a later time the user can check the status of the status of the job and if it is completed download the result of all the requests in the job.