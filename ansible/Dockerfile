FROM ubuntu:22.04

ENV ANSIBLE_HOST_KEY_CHECKING=False

RUN apt-get update -y && apt-get install -y openssh-server sudo python3-apt sshpass

RUN mkdir /var/run/sshd

RUN apt-get install -y ansible

RUN useradd -ms /bin/bash admin


RUN echo 'admin:password' | chpasswd

RUN mkdir /home/admin/.ssh
RUN chown -R admin:admin /home/admin/.ssh
RUN chmod 700 /home/admin/.ssh

EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]