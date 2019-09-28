import boto3
from botocore.exceptions import ClientError

def create(QueueName):
    sqs = boto3.resource('sqs')
    try:
        sqs.create_queue(QueueName=QueueName, Attributes={'FifoQueue':'true', 'ContentBasedDeduplication':'true'})
    except ClientError:
        print('Keys are out-of-date.Go awseducte and get new credentials.')
    except Exception as e:
        print(e)

def delete(QueueName):
    sqs = boto3.resource('sqs')
    try:
        queue = sqs.get_queue_by_name(QueueName=QueueName)
        queue.delete()
    except ClientError:
        print('Keys are out-of-date.Go awseducte and get new credentials.')
    except Exception as e:
        print(e)

delete('test.fifo')
create('test.fifo')